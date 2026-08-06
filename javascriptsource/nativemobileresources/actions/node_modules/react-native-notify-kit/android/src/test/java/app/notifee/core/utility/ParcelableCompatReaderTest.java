package app.notifee.core.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import android.app.Notification;
import android.os.Bundle;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
public class ParcelableCompatReaderTest {

  @Test
  @Config(sdk = 32)
  public void getParcelable_legacyPath_returnsNotificationAndNullWhenMissing() {
    Bundle bundle = new Bundle();
    Notification notification = createNotification();
    bundle.putParcelable("notification", notification);
    bundle.putParcelable("explicitNull", (Notification) null);

    assertEquals(
        notification,
        ParcelableCompatReader.getParcelable(bundle, "notification", Notification.class));
    assertNull(ParcelableCompatReader.getParcelable(bundle, "missing", Notification.class));
    assertNull(ParcelableCompatReader.getParcelable(bundle, "explicitNull", Notification.class));
  }

  @Test
  @Config(sdk = 33)
  public void getParcelable_typedPath_returnsNotificationAndNullWhenMissing() {
    Bundle bundle = new Bundle();
    Notification notification = createNotification();
    bundle.putParcelable("notification", notification);
    bundle.putParcelable("explicitNull", (Notification) null);

    assertEquals(
        notification,
        ParcelableCompatReader.getParcelable(bundle, "notification", Notification.class));
    assertNull(ParcelableCompatReader.getParcelable(bundle, "missing", Notification.class));
    assertNull(ParcelableCompatReader.getParcelable(bundle, "explicitNull", Notification.class));
  }

  @Test
  @Config(sdk = 32)
  public void getParcelable_legacyPath_preservesWrongClassCast() {
    Bundle bundle = new Bundle();
    bundle.putParcelable("notification", createNotification());

    assertThrows(
        ClassCastException.class,
        () -> {
          Bundle ignored =
              ParcelableCompatReader.getParcelable(bundle, "notification", Bundle.class);
        });
  }

  @Test
  @Config(sdk = 33)
  public void getParcelable_typedPath_returnsNullForWrongClass() {
    Bundle bundle = new Bundle();
    bundle.putParcelable("notification", createNotification());

    assertNull(ParcelableCompatReader.getParcelable(bundle, "notification", Bundle.class));
  }

  @Test
  @Config(sdk = 32)
  public void getParcelableArrayList_legacyPath_returnsBundleList() {
    Bundle bundle = new Bundle();
    ArrayList<Bundle> bundles = createBundleList();
    bundle.putParcelableArrayList("bundles", bundles);
    bundle.putParcelableArrayList("explicitNull", null);

    ArrayList<Bundle> result =
        ParcelableCompatReader.getParcelableArrayList(bundle, "bundles", Bundle.class);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("first", result.get(0).getString("name"));
    assertNull(ParcelableCompatReader.getParcelableArrayList(bundle, "missing", Bundle.class));
    assertNull(ParcelableCompatReader.getParcelableArrayList(bundle, "explicitNull", Bundle.class));
  }

  @Test
  @Config(sdk = 33)
  public void getParcelableArrayList_typedPath_returnsBundleList() {
    Bundle bundle = new Bundle();
    ArrayList<Bundle> bundles = createBundleList();
    bundle.putParcelableArrayList("bundles", bundles);
    bundle.putParcelableArrayList("explicitNull", null);

    ArrayList<Bundle> result =
        ParcelableCompatReader.getParcelableArrayList(bundle, "bundles", Bundle.class);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("first", result.get(0).getString("name"));
    assertNull(ParcelableCompatReader.getParcelableArrayList(bundle, "missing", Bundle.class));
    assertNull(ParcelableCompatReader.getParcelableArrayList(bundle, "explicitNull", Bundle.class));
  }

  private static Notification createNotification() {
    return new Notification.Builder(RuntimeEnvironment.getApplication(), "reader-test")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("reader-test")
        .build();
  }

  private static ArrayList<Bundle> createBundleList() {
    Bundle nested = new Bundle();
    nested.putString("name", "first");

    ArrayList<Bundle> bundles = new ArrayList<>();
    bundles.add(nested);
    return bundles;
  }
}
