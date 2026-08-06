package app.notifee.core;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.os.Bundle;
import app.notifee.core.model.NotificationModel;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNotificationManager;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
public class NotificationManagerPressActionOptOutTest {
  private static final String CHANNEL_ID = "press-action-opt-out-test-channel";

  @Before
  public void setUp() {
    Context context = RuntimeEnvironment.getApplication();
    ContextHolder.setApplicationContext(context);
    Shadows.shadowOf(RuntimeEnvironment.getApplication())
        .grantPermissions(Manifest.permission.POST_NOTIFICATIONS);
    createChannel(context);

    android.app.NotificationManager notificationManager =
        (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.cancelAll();
  }

  @Test
  public void displayNotification_optOutPressActionOnAndroid12Plus_hasNoContentIntent()
      throws Exception {
    Notification notification =
        displayNotification(buildNotificationModelWithOptOutPressAction("opt-out-android-12-plus"));

    assertNull(
        "pressAction opt-out must not set a body tap content intent on Android 12+",
        notification.contentIntent);
  }

  @Test
  @Config(sdk = 28)
  public void displayNotification_optOutPressActionPreAndroid12_hasNoContentIntent()
      throws Exception {
    Notification notification =
        displayNotification(buildNotificationModelWithOptOutPressAction("opt-out-pre-android-12"));

    assertNull(
        "pressAction opt-out must not set a body tap content intent before Android 12",
        notification.contentIntent);
  }

  @Test
  public void displayNotification_missingPressAction_keepsContentIntent() throws Exception {
    Notification notification = displayNotification(buildNotificationModel("missing-press-action"));

    assertNotNull(
        "missing pressAction must synthesize the default body tap content intent",
        notification.contentIntent);
  }

  @Test
  public void displayNotification_actionButtonWithOptOutContent_keepsActionIntent()
      throws Exception {
    Notification notification =
        displayNotification(buildNotificationModelWithOptOutPressActionAndAction("opt-out-action"));

    assertNull(
        "pressAction opt-out must only disable the body tap content intent",
        notification.contentIntent);
    assertNotNull("action array must be present", notification.actions);
    assertEquals("one action must be built", 1, notification.actions.length);
    assertNotNull(
        "action button PendingIntent must remain independent of body tap opt-out",
        notification.actions[0].actionIntent);
  }

  private static Notification displayNotification(NotificationModel notificationModel)
      throws Exception {
    ListenableFuture<Void> future =
        NotificationManager.displayNotification(notificationModel, null);
    future.get(5, TimeUnit.SECONDS);

    Context context = RuntimeEnvironment.getApplication();
    android.app.NotificationManager notificationManager =
        (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    ShadowNotificationManager shadowNotificationManager = Shadows.shadowOf(notificationManager);
    Notification notification =
        shadowNotificationManager.getNotification(notificationModel.getHashCode());
    assertNotNull("notification must be posted", notification);
    return notification;
  }

  private static void createChannel(Context context) {
    android.app.NotificationManager notificationManager =
        (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
      NotificationChannel channel =
          new NotificationChannel(
              CHANNEL_ID,
              "Press action opt-out test",
              android.app.NotificationManager.IMPORTANCE_LOW);
      notificationManager.createNotificationChannel(channel);
    }
  }

  private static NotificationModel buildNotificationModel(String id) {
    return NotificationModel.fromBundle(buildNotificationBundle(id));
  }

  private static NotificationModel buildNotificationModelWithOptOutPressAction(String id) {
    Bundle notificationBundle = buildNotificationBundle(id);
    Bundle androidBundle = notificationBundle.getBundle("android");
    androidBundle.putBundle(
        "pressAction", buildPressAction(NotificationPendingIntent.PRESS_ACTION_OPT_OUT_ID));
    return NotificationModel.fromBundle(notificationBundle);
  }

  private static NotificationModel buildNotificationModelWithOptOutPressActionAndAction(String id) {
    Bundle notificationBundle = buildNotificationBundle(id);
    Bundle androidBundle = notificationBundle.getBundle("android");
    androidBundle.putBundle(
        "pressAction", buildPressAction(NotificationPendingIntent.PRESS_ACTION_OPT_OUT_ID));

    Bundle action = new Bundle();
    action.putString("title", "Reply");
    action.putBundle("pressAction", buildPressAction("reply"));
    ArrayList<Bundle> actions = new ArrayList<>();
    actions.add(action);
    androidBundle.putParcelableArrayList("actions", actions);

    return NotificationModel.fromBundle(notificationBundle);
  }

  private static Bundle buildNotificationBundle(String id) {
    Bundle notificationBundle = new Bundle();
    notificationBundle.putString("id", id);
    notificationBundle.putString("title", "Press action opt-out " + id);
    notificationBundle.putString("body", "Body " + id);

    Bundle androidBundle = new Bundle();
    androidBundle.putString("channelId", CHANNEL_ID);
    notificationBundle.putBundle("android", androidBundle);
    return notificationBundle;
  }

  private static Bundle buildPressAction(String id) {
    Bundle pressAction = new Bundle();
    pressAction.putString("id", id);
    return pressAction;
  }
}
