//
//
////
////Source code recreated from a .class file by IntelliJ IDEA
////(powered by Fernflower decompiler)
////
//
//package com.mxs.alibaba.fastjson;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONAware;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.util.TypeUtils;
//import java.io.Serializable;
//import java.lang.reflect.Type;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.RandomAccess;
//
//public class JSONArray extends JSON implements List<Object>, JSONAware, Cloneable, RandomAccess, Serializable {
// private static final long serialVersionUID = 1L;
// private final List<Object> list;
// protected transient Object relatedArray;
// protected transient Type componentType;
//
// public JSONArray() {
//     this.list = new ArrayList(10);
// }
//
// public JSONArray(List<Object> list) {
//     this.list = list;
// }
//
// public JSONArray(int initialCapacity) {
//     this.list = new ArrayList(initialCapacity);
// }
//
// public Object getRelatedArray() {
//     return this.relatedArray;
// }
//
// public void setRelatedArray(Object relatedArray) {
//     this.relatedArray = relatedArray;
// }
//
// public Type getComponentType() {
//     return this.componentType;
// }
//
// public void setComponentType(Type componentType) {
//     this.componentType = componentType;
// }
//
// public int size() {
//     return this.list.size();
// }
//
// public boolean isEmpty() {
//     return this.list.isEmpty();
// }
//
// public boolean contains(Object o) {
//     return this.list.contains(o);
// }
//
// public Iterator<Object> iterator() {
//     return this.list.iterator();
// }
//
// public Object[] toArray() {
//     return this.list.toArray();
// }
//
// public <T> T[] toArray(T[] a) {
//     return this.list.toArray(a);
// }
//
// public boolean add(Object e) {
//     return this.list.add(e);
// }
//
// public boolean remove(Object o) {
//     return this.list.remove(o);
// }
//
// public boolean containsAll(Collection<?> c) {
//     return this.list.containsAll(c);
// }
//
// public boolean addAll(Collection<? extends Object> c) {
//     return this.list.addAll(c);
// }
//
// public boolean addAll(int index, Collection<? extends Object> c) {
//     return this.list.addAll(index, c);
// }
//
// public boolean removeAll(Collection<?> c) {
//     return this.list.removeAll(c);
// }
//
// public boolean retainAll(Collection<?> c) {
//     return this.list.retainAll(c);
// }
//
// public void clear() {
//     this.list.clear();
// }
//
// public Object set(int index, Object element) {
//     return this.list.set(index, element);
// }
//
// public void add(int index, Object element) {
//     this.list.add(index, element);
// }
//
// public Object remove(int index) {
//     return this.list.remove(index);
// }
//
// public int indexOf(Object o) {
//     return this.list.indexOf(o);
// }
//
// public int lastIndexOf(Object o) {
//     return this.list.lastIndexOf(o);
// }
//
// public ListIterator<Object> listIterator() {
//     return this.list.listIterator();
// }
//
// public ListIterator<Object> listIterator(int index) {
//     return this.list.listIterator(index);
// }
//
// public List<Object> subList(int fromIndex, int toIndex) {
//     return this.list.subList(fromIndex, toIndex);
// }
//
// public Object get(int index) {
//     return this.list.get(index);
// }
//
// public JSONObject getJSONObject(int index) {
//     Object value = this.list.get(index);
//     return value instanceof JSONObject?(JSONObject)value:(JSONObject)toJSON(value);
// }
//
// public JSONArray getJSONArray(int index) {
//     Object value = this.list.get(index);
//     return value instanceof JSONArray?(JSONArray)value:(JSONArray)toJSON(value);
// }
//
// public <T> T getObject(int index, Class<T> clazz) {
//     Object obj = this.list.get(index);
//     return TypeUtils.castToJavaBean(obj, clazz);
// }
//
// public Boolean getBoolean(int index) {
//     Object value = this.get(index);
//     return value == null?null:TypeUtils.castToBoolean(value);
// }
//
// public boolean getBooleanValue(int index) {
//     Object value = this.get(index);
//     return value == null?false:TypeUtils.castToBoolean(value).booleanValue();
// }
//
// public Byte getByte(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToByte(value);
// }
//
// public byte getByteValue(int index) {
//     Object value = this.get(index);
//     return value == null?0:TypeUtils.castToByte(value).byteValue();
// }
//
// public Short getShort(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToShort(value);
// }
//
// public short getShortValue(int index) {
//     Object value = this.get(index);
//     return value == null?0:TypeUtils.castToShort(value).shortValue();
// }
//
// public Integer getInteger(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToInt(value);
// }
//
// public int getIntValue(int index) {
//     Object value = this.get(index);
//     return value == null?0:TypeUtils.castToInt(value).intValue();
// }
//
// public Long getLong(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToLong(value);
// }
//
// public long getLongValue(int index) {
//     Object value = this.get(index);
//     return value == null?0L:TypeUtils.castToLong(value).longValue();
// }
//
// public Float getFloat(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToFloat(value);
// }
//
// public float getFloatValue(int index) {
//     Object value = this.get(index);
//     return value == null?0.0F:TypeUtils.castToFloat(value).floatValue();
// }
//
// public Double getDouble(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToDouble(value);
// }
//
// public double getDoubleValue(int index) {
//     Object value = this.get(index);
//     return value == null?0.0D:TypeUtils.castToDouble(value).doubleValue();
// }
//
// public BigDecimal getBigDecimal(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToBigDecimal(value);
// }
//
// public BigInteger getBigInteger(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToBigInteger(value);
// }
//
// public String getString(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToString(value);
// }
//
// public Date getDate(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToDate(value);
// }
//
// public java.sql.Date getSqlDate(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToSqlDate(value);
// }
//
// public Timestamp getTimestamp(int index) {
//     Object value = this.get(index);
//     return TypeUtils.castToTimestamp(value);
// }
//
// public Object clone() {
//     return new JSONArray(new ArrayList(this.list));
// }
//
// public boolean equals(Object obj) {
//     return this.list.equals(obj);
// }
//
// public int hashCode() {
//     return this.list.hashCode();
// }
//}
//
