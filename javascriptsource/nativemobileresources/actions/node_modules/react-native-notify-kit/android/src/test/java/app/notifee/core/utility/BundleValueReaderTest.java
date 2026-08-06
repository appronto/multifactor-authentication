package app.notifee.core.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import android.os.Bundle;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class BundleValueReaderTest {

  @Test
  public void getValue_returnsRawValueAndNullForMissingAndExplicitNull() {
    Bundle bundle = new Bundle();
    bundle.putString("string", "value");
    bundle.putString("explicitNull", null);

    assertEquals("value", BundleValueReader.getValue(bundle, "string"));
    assertNull(BundleValueReader.getValue(bundle, "missing"));
    assertNull(BundleValueReader.getValue(bundle, "explicitNull"));
  }

  @Test
  public void getValue_nullBundleThrowsNullPointerException() {
    assertThrows(NullPointerException.class, () -> BundleValueReader.getValue(null, "key"));
  }

  @Test
  public void getIntPreserving_matchesCurrentObjectUtilsBehavior() {
    Bundle bundle = new Bundle();
    bundle.putInt("integer", 7);
    bundle.putDouble("double", 7.9d);
    bundle.putString("explicitNull", null);
    bundle.putLong("long", 7L);
    bundle.putFloat("float", 7.5f);
    bundle.putString("string", "7");

    assertEquals(7, BundleValueReader.getIntPreserving(bundle, "integer"));
    assertEquals(7, BundleValueReader.getIntPreserving(bundle, "double"));
    assertEquals(0, BundleValueReader.getIntPreserving(bundle, "missing"));
    assertEquals(0, BundleValueReader.getIntPreserving(bundle, "explicitNull"));

    assertThrows(
        ClassCastException.class, () -> BundleValueReader.getIntPreserving(bundle, "long"));
    assertThrows(
        ClassCastException.class, () -> BundleValueReader.getIntPreserving(bundle, "float"));
    assertThrows(
        ClassCastException.class, () -> BundleValueReader.getIntPreserving(bundle, "string"));
  }

  @Test
  public void getIntPreserving_withDefault_distinguishesMissingFromExplicitNull() {
    Bundle bundle = new Bundle();
    bundle.putString("explicitNull", null);
    bundle.putLong("long", 7L);

    assertEquals(42, BundleValueReader.getIntPreserving(bundle, "missing", 42));
    assertEquals(0, BundleValueReader.getIntPreserving(bundle, "explicitNull", 42));
    assertThrows(
        ClassCastException.class, () -> BundleValueReader.getIntPreserving(bundle, "long", 42));
  }

  @Test
  public void getLongPreserving_matchesCurrentObjectUtilsBehavior() {
    Bundle bundle = new Bundle();
    bundle.putLong("long", 9000000000L);
    bundle.putDouble("double", 7.9d);
    bundle.putString("explicitNull", null);
    bundle.putInt("integer", 7);
    bundle.putFloat("float", 7.5f);
    bundle.putString("string", "7");

    assertEquals(9000000000L, BundleValueReader.getLongPreserving(bundle, "long"));
    assertEquals(7L, BundleValueReader.getLongPreserving(bundle, "double"));

    assertThrows(
        NullPointerException.class, () -> BundleValueReader.getLongPreserving(bundle, "missing"));
    assertThrows(
        NullPointerException.class,
        () -> BundleValueReader.getLongPreserving(bundle, "explicitNull"));
    assertThrows(
        ClassCastException.class, () -> BundleValueReader.getLongPreserving(bundle, "integer"));
    assertThrows(
        ClassCastException.class, () -> BundleValueReader.getLongPreserving(bundle, "float"));
    assertThrows(
        ClassCastException.class, () -> BundleValueReader.getLongPreserving(bundle, "string"));
  }

  @Test
  public void getLongPreserving_withDefault_distinguishesMissingFromExplicitNull() {
    Bundle bundle = new Bundle();
    bundle.putString("explicitNull", null);
    bundle.putInt("integer", 7);

    assertEquals(42L, BundleValueReader.getLongPreserving(bundle, "missing", 42L));
    assertThrows(
        NullPointerException.class,
        () -> BundleValueReader.getLongPreserving(bundle, "explicitNull", 42L));
    assertThrows(
        ClassCastException.class,
        () -> BundleValueReader.getLongPreserving(bundle, "integer", 42L));
  }

  @Test
  public void getArrayListValue_returnsRawArrayListAndNullForMissingAndExplicitNull() {
    ArrayList<String> values = new ArrayList<>();
    values.add("one");
    values.add("two");

    Bundle bundle = new Bundle();
    bundle.putStringArrayList("values", values);
    bundle.putStringArrayList("explicitNull", null);

    assertSame(values, BundleValueReader.getArrayListValue(bundle, "values"));
    assertNull(BundleValueReader.getArrayListValue(bundle, "missing"));
    assertNull(BundleValueReader.getArrayListValue(bundle, "explicitNull"));
  }

  @Test
  public void getArrayListValue_preservesTopLevelCastFailure() {
    Bundle bundle = new Bundle();
    bundle.putString("wrongType", "value");

    assertThrows(
        ClassCastException.class, () -> BundleValueReader.getArrayListValue(bundle, "wrongType"));
  }
}
