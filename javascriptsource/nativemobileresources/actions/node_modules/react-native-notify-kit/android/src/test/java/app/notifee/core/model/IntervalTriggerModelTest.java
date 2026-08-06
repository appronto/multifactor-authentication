package app.notifee.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import android.os.Bundle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class IntervalTriggerModelTest {

  @Test
  public void getInterval_returnsMinusOneWhenMissing() {
    IntervalTriggerModel model = IntervalTriggerModel.fromBundle(new Bundle());

    assertEquals(-1, model.getInterval());
  }

  @Test
  public void getInterval_returnsIntegerValue() {
    Bundle bundle = new Bundle();
    bundle.putInt("interval", 15);

    IntervalTriggerModel model = IntervalTriggerModel.fromBundle(bundle);

    assertEquals(15, model.getInterval());
  }

  @Test
  public void getInterval_truncatesDoubleValue() {
    Bundle bundle = new Bundle();
    bundle.putDouble("interval", 15.9d);

    IntervalTriggerModel model = IntervalTriggerModel.fromBundle(bundle);

    assertEquals(15, model.getInterval());
  }

  @Test
  public void getInterval_returnsZeroForExplicitNull() {
    Bundle bundle = new Bundle();
    bundle.putString("interval", null);

    IntervalTriggerModel model = IntervalTriggerModel.fromBundle(bundle);

    assertEquals(0, model.getInterval());
  }

  @Test
  public void getInterval_preservesClassCastForUnsupportedNumericTypes() {
    Bundle longBundle = new Bundle();
    longBundle.putLong("interval", 15L);
    assertThrows(
        ClassCastException.class, () -> IntervalTriggerModel.fromBundle(longBundle).getInterval());

    Bundle floatBundle = new Bundle();
    floatBundle.putFloat("interval", 15.9f);
    assertThrows(
        ClassCastException.class, () -> IntervalTriggerModel.fromBundle(floatBundle).getInterval());
  }

  @Test
  public void getInterval_preservesClassCastForString() {
    Bundle bundle = new Bundle();
    bundle.putString("interval", "15");

    assertThrows(
        ClassCastException.class, () -> IntervalTriggerModel.fromBundle(bundle).getInterval());
  }
}
