package app.notifee.core.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import android.content.pm.ServiceInfo;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
public class NotificationAndroidModelTest {

  @Test
  public void numericFields_whenMissing_returnCurrentDefaults() {
    NotificationAndroidModel model = NotificationAndroidModel.fromBundle(new Bundle());

    assertEquals(NotificationCompat.BADGE_ICON_LARGE, model.getBadgeIconType().intValue());
    assertEquals(NotificationCompat.GROUP_ALERT_ALL, model.getGroupAlertBehaviour());
    assertNull(model.getNumber());
    assertEquals(NotificationCompat.PRIORITY_DEFAULT, model.getPriority());
    assertNull(model.getProgress());
    assertNull(model.getTimeoutAfter());
    assertEquals(NotificationCompat.VISIBILITY_PRIVATE, model.getVisibility());
    assertEquals(-1L, model.getTimestamp());
  }

  @Test
  public void numericFields_withIntegerAndLongValues_returnParsedValues() {
    Bundle progress = new Bundle();
    progress.putInt("max", 100);
    progress.putInt("current", 40);
    progress.putBoolean("indeterminate", true);

    Bundle bundle = new Bundle();
    bundle.putInt("badgeIconType", NotificationCompat.BADGE_ICON_SMALL);
    bundle.putInt("groupAlertBehavior", NotificationCompat.GROUP_ALERT_SUMMARY);
    bundle.putInt("badgeCount", 8);
    bundle.putInt("importance", NotificationManagerCompat.IMPORTANCE_HIGH);
    bundle.putBundle("progress", progress);
    bundle.putLong("timeoutAfter", 5000L);
    bundle.putInt("visibility", NotificationCompat.VISIBILITY_SECRET);
    bundle.putLong("timestamp", 123456789L);

    NotificationAndroidModel model = NotificationAndroidModel.fromBundle(bundle);

    assertEquals(NotificationCompat.BADGE_ICON_SMALL, model.getBadgeIconType().intValue());
    assertEquals(NotificationCompat.GROUP_ALERT_SUMMARY, model.getGroupAlertBehaviour());
    assertEquals(8, model.getNumber().intValue());
    assertEquals(NotificationCompat.PRIORITY_HIGH, model.getPriority());
    assertNotNull(model.getProgress());
    assertEquals(100, model.getProgress().getMax());
    assertEquals(40, model.getProgress().getCurrent());
    assertEquals(true, model.getProgress().getIndeterminate());
    assertEquals(5000L, model.getTimeoutAfter().longValue());
    assertEquals(NotificationCompat.VISIBILITY_SECRET, model.getVisibility());
    assertEquals(123456789L, model.getTimestamp());
  }

  @Test
  public void numericFields_withDoubleValues_truncateWhereCurrentObjectUtilsSupportsDouble() {
    Bundle progress = new Bundle();
    progress.putDouble("max", 100.9d);
    progress.putDouble("current", 40.8d);

    Bundle bundle = new Bundle();
    bundle.putDouble("badgeIconType", 1.9d);
    bundle.putDouble("groupAlertBehavior", 2.9d);
    bundle.putDouble("badgeCount", 8.9d);
    bundle.putDouble("importance", NotificationManagerCompat.IMPORTANCE_HIGH + 0.9d);
    bundle.putBundle("progress", progress);
    bundle.putDouble("timeoutAfter", 5000.9d);
    bundle.putDouble("visibility", -1.9d);
    bundle.putDouble("timestamp", 123456789.9d);

    NotificationAndroidModel model = NotificationAndroidModel.fromBundle(bundle);

    assertEquals(1, model.getBadgeIconType().intValue());
    assertEquals(2, model.getGroupAlertBehaviour());
    assertEquals(8, model.getNumber().intValue());
    assertEquals(NotificationCompat.PRIORITY_HIGH, model.getPriority());
    assertNotNull(model.getProgress());
    assertEquals(100, model.getProgress().getMax());
    assertEquals(40, model.getProgress().getCurrent());
    assertEquals(5000L, model.getTimeoutAfter().longValue());
    assertEquals(NotificationCompat.VISIBILITY_SECRET, model.getVisibility());
    assertEquals(123456789L, model.getTimestamp());
  }

  @Test
  public void intBackedNumericFields_withExplicitNull_currentlyCoerceToZero() {
    Bundle progress = new Bundle();
    progress.putString("max", null);
    progress.putString("current", null);

    Bundle bundle = new Bundle();
    bundle.putString("badgeIconType", null);
    bundle.putString("groupAlertBehavior", null);
    bundle.putString("badgeCount", null);
    bundle.putBundle("progress", progress);
    bundle.putString("visibility", null);

    NotificationAndroidModel model = NotificationAndroidModel.fromBundle(bundle);

    assertEquals(0, model.getBadgeIconType().intValue());
    assertEquals(0, model.getGroupAlertBehaviour());
    assertEquals(0, model.getNumber().intValue());
    assertNotNull(model.getProgress());
    assertEquals(0, model.getProgress().getMax());
    assertEquals(0, model.getProgress().getCurrent());
    assertEquals(0, model.getVisibility());
  }

  @Test
  public void longBackedNumericFields_withExplicitNull_currentlyThrowNullPointerException() {
    Bundle timeoutBundle = new Bundle();
    timeoutBundle.putString("timeoutAfter", null);
    assertThrows(
        NullPointerException.class,
        () -> NotificationAndroidModel.fromBundle(timeoutBundle).getTimeoutAfter());

    Bundle timestampBundle = new Bundle();
    timestampBundle.putString("timestamp", null);
    assertThrows(
        NullPointerException.class,
        () -> NotificationAndroidModel.fromBundle(timestampBundle).getTimestamp());
  }

  @Test
  public void numericFields_withUnsupportedNumericTypes_currentlyThrowClassCastException() {
    Bundle badgeBundle = new Bundle();
    badgeBundle.putLong("badgeIconType", 1L);
    assertThrows(
        ClassCastException.class,
        () -> NotificationAndroidModel.fromBundle(badgeBundle).getBadgeIconType());

    Bundle timestampBundle = new Bundle();
    timestampBundle.putInt("timestamp", 1);
    assertThrows(
        ClassCastException.class,
        () -> NotificationAndroidModel.fromBundle(timestampBundle).getTimestamp());

    Bundle timeoutBundle = new Bundle();
    timeoutBundle.putString("timeoutAfter", "5000");
    assertThrows(
        ClassCastException.class,
        () -> NotificationAndroidModel.fromBundle(timeoutBundle).getTimeoutAfter());
  }

  @Test
  public void actions_missingEmptyAndPresent_preserveParcelableArrayListBehavior() {
    assertNull(NotificationAndroidModel.fromBundle(new Bundle()).getActions());

    Bundle emptyBundle = new Bundle();
    putRawParcelableArrayList(emptyBundle, "actions", new ArrayList<Bundle>());
    assertEquals(0, NotificationAndroidModel.fromBundle(emptyBundle).getActions().size());

    Bundle pressAction = new Bundle();
    pressAction.putString("id", "reply");
    Bundle action = new Bundle();
    action.putString("title", "Reply");
    action.putString("icon", "ic_reply");
    action.putBundle("pressAction", pressAction);
    ArrayList<Bundle> actions = new ArrayList<>();
    actions.add(action);

    Bundle bundle = new Bundle();
    putRawParcelableArrayList(bundle, "actions", actions);
    ArrayList<NotificationAndroidActionModel> parsedActions =
        NotificationAndroidModel.fromBundle(bundle).getActions();

    assertNotNull(parsedActions);
    assertEquals(1, parsedActions.size());
    assertEquals("Reply", parsedActions.get(0).getTitle());
    assertEquals("ic_reply", parsedActions.get(0).getIcon());
    assertEquals("reply", parsedActions.get(0).getPressAction().getId());
  }

  @Test
  @Config(sdk = 33)
  public void foregroundServiceTypes_missingEmptyAndPresent_preserveCurrentParsing() {
    assertEquals(
        ServiceInfo.FOREGROUND_SERVICE_TYPE_MANIFEST,
        NotificationAndroidModel.fromBundle(new Bundle()).getForegroundServiceType());

    Bundle emptyBundle = new Bundle();
    putRawParcelableArrayList(emptyBundle, "foregroundServiceTypes", new ArrayList<Integer>());
    assertEquals(
        ServiceInfo.FOREGROUND_SERVICE_TYPE_MANIFEST,
        NotificationAndroidModel.fromBundle(emptyBundle).getForegroundServiceType());

    ArrayList<Object> types = new ArrayList<>();
    types.add(ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC);
    types.add(Double.valueOf(ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK + 0.9d));
    Bundle bundle = new Bundle();
    putRawParcelableArrayList(bundle, "foregroundServiceTypes", types);

    assertEquals(
        ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            | ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK,
        NotificationAndroidModel.fromBundle(bundle).getForegroundServiceType());
  }

  @Test
  @Config(sdk = 33)
  public void foregroundServiceTypes_withUnsupportedValue_currentlyThrowsClassCastException() {
    ArrayList<Object> types = new ArrayList<>();
    types.add(Long.valueOf(ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC));
    Bundle bundle = new Bundle();
    putRawParcelableArrayList(bundle, "foregroundServiceTypes", types);

    assertThrows(
        ClassCastException.class,
        () -> NotificationAndroidModel.fromBundle(bundle).getForegroundServiceType());
  }

  @Test
  public void lights_missingValidAndInvalidValues_preserveCurrentNullOnParseFailure() {
    assertNull(NotificationAndroidModel.fromBundle(new Bundle()).getLights());

    ArrayList<Object> lights = new ArrayList<>();
    lights.add("#112233");
    lights.add(100);
    lights.add(200);
    Bundle validBundle = new Bundle();
    putRawParcelableArrayList(validBundle, "lights", lights);

    ArrayList<Integer> parsedLights = NotificationAndroidModel.fromBundle(validBundle).getLights();
    assertNotNull(parsedLights);
    assertEquals(Color.parseColor("#112233"), parsedLights.get(0).intValue());
    assertEquals(100, parsedLights.get(1).intValue());
    assertEquals(200, parsedLights.get(2).intValue());

    ArrayList<Object> invalidDuration = new ArrayList<>();
    invalidDuration.add("#112233");
    invalidDuration.add(100L);
    invalidDuration.add(200);
    Bundle invalidBundle = new Bundle();
    putRawParcelableArrayList(invalidBundle, "lights", invalidDuration);

    assertNull(NotificationAndroidModel.fromBundle(invalidBundle).getLights());
  }

  @Test
  public void flags_missingValidAndUnsupportedValues_preserveCurrentBehavior() {
    assertNull(NotificationAndroidModel.fromBundle(new Bundle()).getFlags());

    ArrayList<Object> flags = new ArrayList<>();
    flags.add(1);
    flags.add(2.9d);
    Bundle validBundle = new Bundle();
    putRawParcelableArrayList(validBundle, "flags", flags);

    assertArrayEquals(
        new int[] {1, 2}, NotificationAndroidModel.fromBundle(validBundle).getFlags());

    ArrayList<Object> invalidFlags = new ArrayList<>();
    invalidFlags.add(1L);
    Bundle invalidBundle = new Bundle();
    putRawParcelableArrayList(invalidBundle, "flags", invalidFlags);

    assertThrows(
        ClassCastException.class,
        () -> NotificationAndroidModel.fromBundle(invalidBundle).getFlags());
  }

  @Test
  public void vibrationPattern_missingEmptyIntegerAndUnsupportedValues_preserveCurrentBehavior() {
    assertArrayEquals(
        new long[0], NotificationAndroidModel.fromBundle(new Bundle()).getVibrationPattern());

    Bundle emptyBundle = new Bundle();
    putRawParcelableArrayList(emptyBundle, "vibrationPattern", new ArrayList<Integer>());
    assertArrayEquals(
        new long[0], NotificationAndroidModel.fromBundle(emptyBundle).getVibrationPattern());

    ArrayList<Integer> vibrationPattern = new ArrayList<>();
    vibrationPattern.add(100);
    vibrationPattern.add(200);
    Bundle validBundle = new Bundle();
    putRawParcelableArrayList(validBundle, "vibrationPattern", vibrationPattern);

    assertArrayEquals(
        new long[] {100L, 200L},
        NotificationAndroidModel.fromBundle(validBundle).getVibrationPattern());

    ArrayList<Object> invalidPattern = new ArrayList<>();
    invalidPattern.add(100L);
    Bundle invalidBundle = new Bundle();
    putRawParcelableArrayList(invalidBundle, "vibrationPattern", invalidPattern);

    assertThrows(
        ClassCastException.class,
        () -> NotificationAndroidModel.fromBundle(invalidBundle).getVibrationPattern());
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void putRawParcelableArrayList(Bundle bundle, String key, ArrayList<?> arrayList) {
    bundle.putParcelableArrayList(key, (ArrayList) arrayList);
  }
}
