package app.notifee.core.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ChannelModelTest {

  @Test
  public void fields_whenMissing_returnCurrentDefaults() {
    ChannelModel model = ChannelModel.fromBundle(new Bundle());

    assertEquals(NotificationManagerCompat.IMPORTANCE_DEFAULT, model.getImportance().intValue());
    assertEquals(NotificationCompat.VISIBILITY_PRIVATE, model.getVisibility());
    assertArrayEquals(new long[0], model.getVibrationPattern());
    assertNull(model.getSound());
  }

  @Test
  public void numericFields_withIntegerAndDoubleValues_preserveCurrentCoercion() {
    Bundle integerBundle = new Bundle();
    integerBundle.putInt("importance", NotificationManagerCompat.IMPORTANCE_HIGH);
    integerBundle.putInt("visibility", NotificationCompat.VISIBILITY_SECRET);
    ChannelModel integerModel = ChannelModel.fromBundle(integerBundle);

    assertEquals(
        NotificationManagerCompat.IMPORTANCE_HIGH, integerModel.getImportance().intValue());
    assertEquals(NotificationCompat.VISIBILITY_SECRET, integerModel.getVisibility());

    Bundle doubleBundle = new Bundle();
    doubleBundle.putDouble("importance", NotificationManagerCompat.IMPORTANCE_HIGH + 0.9d);
    doubleBundle.putDouble("visibility", -1.9d);
    ChannelModel doubleModel = ChannelModel.fromBundle(doubleBundle);

    assertEquals(NotificationManagerCompat.IMPORTANCE_HIGH, doubleModel.getImportance().intValue());
    assertEquals(NotificationCompat.VISIBILITY_SECRET, doubleModel.getVisibility());
  }

  @Test
  public void numericFields_withExplicitNull_currentlyCoerceToZero() {
    Bundle bundle = new Bundle();
    bundle.putString("importance", null);
    bundle.putString("visibility", null);
    ChannelModel model = ChannelModel.fromBundle(bundle);

    assertEquals(0, model.getImportance().intValue());
    assertEquals(0, model.getVisibility());
  }

  @Test
  public void numericFields_withUnsupportedTypes_currentlyThrowClassCastException() {
    Bundle importanceBundle = new Bundle();
    importanceBundle.putLong("importance", 4L);
    assertThrows(
        ClassCastException.class, () -> ChannelModel.fromBundle(importanceBundle).getImportance());

    Bundle visibilityBundle = new Bundle();
    visibilityBundle.putString("visibility", "1");
    assertThrows(
        ClassCastException.class, () -> ChannelModel.fromBundle(visibilityBundle).getVisibility());
  }

  @Test
  public void vibrationPattern_missingEmptyIntegerAndUnsupportedValues_preserveCurrentBehavior() {
    assertArrayEquals(new long[0], ChannelModel.fromBundle(new Bundle()).getVibrationPattern());

    Bundle emptyBundle = new Bundle();
    putRawParcelableArrayList(emptyBundle, "vibrationPattern", new ArrayList<Integer>());
    assertArrayEquals(new long[0], ChannelModel.fromBundle(emptyBundle).getVibrationPattern());

    ArrayList<Integer> vibrationPattern = new ArrayList<>();
    vibrationPattern.add(100);
    vibrationPattern.add(200);
    Bundle validBundle = new Bundle();
    putRawParcelableArrayList(validBundle, "vibrationPattern", vibrationPattern);

    assertArrayEquals(
        new long[] {100L, 200L}, ChannelModel.fromBundle(validBundle).getVibrationPattern());

    ArrayList<Object> invalidPattern = new ArrayList<>();
    invalidPattern.add(100L);
    Bundle invalidBundle = new Bundle();
    putRawParcelableArrayList(invalidBundle, "vibrationPattern", invalidPattern);

    assertThrows(
        ClassCastException.class,
        () -> ChannelModel.fromBundle(invalidBundle).getVibrationPattern());
  }

  @Test
  public void sound_missingPresentAndExplicitNull_preserveCurrentBehavior() {
    assertNull(ChannelModel.fromBundle(new Bundle()).getSound());

    Bundle soundBundle = new Bundle();
    soundBundle.putString("sound", "default");
    assertEquals("default", ChannelModel.fromBundle(soundBundle).getSound());

    Bundle nullSoundBundle = new Bundle();
    nullSoundBundle.putString("sound", null);
    assertNull(ChannelModel.fromBundle(nullSoundBundle).getSound());
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void putRawParcelableArrayList(Bundle bundle, String key, ArrayList<?> arrayList) {
    bundle.putParcelableArrayList(key, (ArrayList) arrayList);
  }
}
