package app.notifee.core.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ObjectUtilsTest {

  @Test
  public void getInt_preservesCurrentCoercionAndFragility() {
    assertEquals(7, ObjectUtils.getInt(Integer.valueOf(7)));
    assertEquals(7, ObjectUtils.getInt(Double.valueOf(7.9d)));
    assertEquals(0, ObjectUtils.getInt(null));

    assertThrows(ClassCastException.class, () -> ObjectUtils.getInt(Long.valueOf(7L)));
    assertThrows(ClassCastException.class, () -> ObjectUtils.getInt(Float.valueOf(7.5f)));
    assertThrows(ClassCastException.class, () -> ObjectUtils.getInt("7"));
  }

  @Test
  public void getLong_preservesCurrentCoercionAndFragility() {
    assertEquals(9000000000L, ObjectUtils.getLong(Long.valueOf(9000000000L)));
    assertEquals(7L, ObjectUtils.getLong(Double.valueOf(7.9d)));

    assertThrows(ClassCastException.class, () -> ObjectUtils.getLong(Integer.valueOf(7)));
    assertThrows(ClassCastException.class, () -> ObjectUtils.getLong(Float.valueOf(7.5f)));
    assertThrows(ClassCastException.class, () -> ObjectUtils.getLong("7"));
    assertThrows(NullPointerException.class, () -> ObjectUtils.getLong(null));
  }

  @Test
  public void bundleToMap_preservesNestedBundlesListsArraysAndNullValues() {
    Bundle nested = new Bundle();
    nested.putString("name", "nested");
    nested.putInt("count", 3);

    ArrayList<Object> nestedList = new ArrayList<>();
    nestedList.add("item");
    nestedList.add(4);
    nestedList.add(nested);

    Bundle bundle = new Bundle();
    bundle.putString("string", "value");
    bundle.putString("nullString", null);
    bundle.putInt("integer", 5);
    bundle.putLong("long", 6L);
    bundle.putDouble("double", 7.5d);
    bundle.putBoolean("boolean", true);
    bundle.putBundle("nestedBundle", nested);
    bundle.putIntArray("intArray", new int[] {1, 2});
    bundle.putDoubleArray("doubleArray", new double[] {1.25d, 2.5d});
    bundle.putBooleanArray("booleanArray", new boolean[] {true, false});
    bundle.putStringArray("stringArray", new String[] {"a", "b"});
    putRawParcelableArrayList(bundle, "nestedList", nestedList);

    Map<String, Object> map = ObjectUtils.bundleToMap(bundle);

    assertEquals("value", map.get("string"));
    assertTrue(map.containsKey("nullString"));
    assertNull(map.get("nullString"));
    assertEquals(5, map.get("integer"));
    assertEquals(6L, map.get("long"));
    assertEquals(7.5d, map.get("double"));
    assertEquals(true, map.get("boolean"));
    assertEquals(Arrays.asList(1, 2), map.get("intArray"));
    assertEquals(Arrays.asList(1.25d, 2.5d), map.get("doubleArray"));
    assertEquals(Arrays.asList(true, false), map.get("booleanArray"));
    assertEquals(Arrays.asList("a", "b"), map.get("stringArray"));

    Map<?, ?> nestedMap = (Map<?, ?>) map.get("nestedBundle");
    assertEquals("nested", nestedMap.get("name"));
    assertEquals(3, nestedMap.get("count"));

    ArrayList<?> convertedList = (ArrayList<?>) map.get("nestedList");
    assertEquals("item", convertedList.get(0));
    assertEquals(4, convertedList.get(1));
    assertEquals("nested", ((Map<?, ?>) convertedList.get(2)).get("name"));
  }

  @Test
  public void listToMap_preservesRecursiveConversionAndNumberRules() {
    Bundle nested = new Bundle();
    nested.putString("name", "nested");

    ArrayList<Object> innerList = new ArrayList<>();
    innerList.add("inner");
    innerList.add(2L);

    ArrayList<Object> list = new ArrayList<>();
    list.add(null);
    list.add("text");
    list.add(1);
    list.add(2L);
    list.add(3.5f);
    list.add(true);
    list.add(nested);
    list.add(new int[] {4, 5});
    list.add(innerList);

    ArrayList<Object> converted = ObjectUtils.listToMap(list);

    assertNull(converted.get(0));
    assertEquals("text", converted.get(1));
    assertEquals(1, converted.get(2));
    assertEquals(2.0d, converted.get(3));
    assertEquals(3.5d, converted.get(4));
    assertEquals(true, converted.get(5));
    assertEquals("nested", ((Map<?, ?>) converted.get(6)).get("name"));
    assertEquals(Arrays.asList(4, 5), converted.get(7));

    ArrayList<?> convertedInnerList = (ArrayList<?>) converted.get(8);
    assertEquals("inner", convertedInnerList.get(0));
    assertEquals(2.0d, convertedInnerList.get(1));
  }

  @Test
  public void bundleAndListConversion_throwForUnsupportedValueTypes() {
    Bundle bundle = new Bundle();
    bundle.putCharArray("unsupportedArray", new char[] {'a'});
    assertThrows(IllegalArgumentException.class, () -> ObjectUtils.bundleToMap(bundle));

    ArrayList<Object> list = new ArrayList<>();
    list.add(new Object());
    assertThrows(IllegalArgumentException.class, () -> ObjectUtils.listToMap(list));
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void putRawParcelableArrayList(Bundle bundle, String key, ArrayList<?> arrayList) {
    bundle.putParcelableArrayList(key, (ArrayList) arrayList);
  }
}
