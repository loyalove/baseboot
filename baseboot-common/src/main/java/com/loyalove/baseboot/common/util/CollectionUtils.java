package com.loyalove.baseboot.common.util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

import static jdk.nashorn.internal.objects.NativeArray.filter;

/**
 * 集合工具。做为 {@link Collections} 的补充与工具包中的 "collection" 类的支持。
 * 
 * <p>
 * 如果没有特殊说明，在调研该类的“包装器”方法时，如果传递参数为 null 则会抛出 {@link IllegalArgumentException} 。
 * 
 */
public abstract class CollectionUtils {
	
	static final int HASH_MAP_DEFAULT_INITIAL_CAPACITY = 16;
	
	static final float HASH_MAP_DEFAULT_LOAD_FACTOR = 0.75f;
	
	/**
	 * 得到Collection的第一个元素。
	 * @param c 需要得到第一个元素的Collection。
	 * @return 对应的第一个元素，如果 c 为null 或者 empty ，则返回 null 。
	 * @since 2.1
	 */
	public static <E> E getFirst(Collection<E> c) {
		if (c == null) {
			return null;
		}
		if (c instanceof List) {
			List<E> list = (List<E>) c;
			return list.isEmpty() ? null : list.get(0);
		} else if (c instanceof Set) {
			return c.isEmpty() ? null : c.iterator().next();
		} else if (c instanceof Queue) {
			return c.isEmpty() ? null : ((Queue<E>) c).peek();
		} else {
			return c.isEmpty() ? null : c.iterator().next();
		}
	}
	
	/**
	 * 使用 element 拆分一个 List 。
	 * <p>
	 * 假设 list 中的元素为 e ，则比较使用
	 * <code>element == null ? e == null : element.equals(e)</code>作为判断依据。
	 * <p>
	 * element 作为被拆分对象会被舍去。
	 * 
	 * @param list 需要拆分的 List 。
	 * @param element 拆分元素。
	 * @return 根据 element 对 list 进行拆分的结果，如果 list 为 null 则返回空的 List 。
	 */
	public static <E> List<List<E>> split(List<E> list, E element) {
		if (list == null) {
			return new ArrayList<List<E>>(0);
		}
		List<List<E>> result = new ArrayList<List<E>>();
		int start = 0, end = 0;
		for (E e : list) {
			if (element == null ? e == null : element.equals(e)) {
				result.add(new ArrayList<E>(list.subList(start, end)));
				start = end + 1;
			}
			end++;
		}
		if (start == list.size()) {
			result.add(new ArrayList<E>(0));
		} else if (start < list.size()) {
			result.add(new ArrayList<E>(list.subList(start, list.size())));
		}
		return result;
	}
	
	/**
	 * 将 list 使用 index 指定的下标位置进行拆分。
	 * <p>
	 * index 下标不包含在被拆分的前段而在后段。
	 * 
	 * @param list 需要拆分的 List 。
	 * @param index 拆分 list 的下标，不能超过 list 的长度。
	 * @return 根据 index 对 list 拆分的结果，如果 list 为 null 则返回空的 List 。
	 * @throws IllegalArgumentException 如果 index 数量超过 list 长度，或者 index
	 * 的不是每个值都比前一个大，或者 index 值大于 list 长度。
	 */
	public static <E> List<List<E>> split(List<E> list, int... index) {
		if (list == null) {
			return new ArrayList<List<E>>(0);
		}
		if (index == null || index.length == 0) {
			ArrayList<List<E>> arrayList = new ArrayList<List<E>>();
			arrayList.add(new ArrayList<E>(list));
			return arrayList;
		}
		if (index.length > list.size()) {
			throw new IllegalArgumentException("{index}数量不能大于{list}长度。");
		}
		List<List<E>> result = new ArrayList<List<E>>();
		int temp = 0;
		for (int i : index) {
			if (i < temp) {
				throw new IllegalArgumentException("{index}的每个值必须比前一个大。");
			}
			if (i > list.size()) {
				throw new IllegalArgumentException("{index}值不能大于{list}长度。");
			}
			result.add(new ArrayList<E>(list.subList(temp, i)));
			temp = i;
		}
		if (temp == list.size()) {
			result.add(new ArrayList<E>(0));
		} else if (temp < list.size()) {
			result.add(new ArrayList<E>(list.subList(temp, list.size())));
		}
		return result;
	}
	
	/**
	 * 过滤一个 collection ，排除掉 elements 相等的元素。
	 * <p>
	 * 假设 collection 中的元素为 e ，则比较使用
	 * <code>element == null ? e == null : element.equals(e)</code>作为判断依据。
	 * 
	 * @param collection 需要过滤的 collection 。
	 * @param elements 需要排除的元素列表。
	 * @throws UnsupportedOperationException 如果 collection 的迭代器不支持修改操作。
	 */
	public static <C extends Collection<?>> void filterExclude(C collection, Object... elements) {
		filter(collection, true, elements);
	}
	
	/**
	 * 过滤一个 collection ，只保留 elements 相等的元素。
	 * <p>
	 * 假设 collection 中的元素为 e ，则比较使用
	 * <code>element == null ? e == null : element.equals(e)</code>作为判断依据。
	 * 
	 * @param collection 需要过滤的 collection 。
	 * @param elements 需要保留的元素列表。
	 * @throws UnsupportedOperationException 如果 collection 的迭代器不支持修改操作。
	 */
	public static <C extends Collection<?>> void filterInclude(C collection, Object... elements) {
		filter(collection, false, elements);
	}

	/**
	 * 包装一个数组为 {@link List}，数组的修改会影响到返回的 {@link List}。
	 * <p>
	 * 返回的 {@link List} 不允许任何 add 与 clear 等改变 array 性质的操作，但是允许 set 操作。
	 * 
	 * @param array 要包装的数组。
	 * @return 包装着数组 array 的 {@link List}，如果 array 为 null 则返回 null。
	 */
	public static <T> List<T> wrap(final T[] array) {
		if (array == null) {
			return null;
		}
		return new AbstractList<T>() {
			
			public T get(int index) {
				return array[index];
			}
			
			public int size() {
				return array.length;
			}
			
			@Override
			public T set(int index, T element) {
				T old = array[index];
				array[index] = element;
				return old;
			}
			
			@Override
			public void clear() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public int indexOf(Object o) {
				if (o == null) {
					for (int i = 0; i < array.length; i++) {
						if (null == array[i]) {
							return i;
						}
					}
				} else {
					for (int i = 0; i < array.length; i++) {
						if (o.equals(array[i])) {
							return i;
						}
					}
				}
				return -1;
			}
			
			@Override
			public int lastIndexOf(Object o) {
				if (o == null) {
					for (int i = array.length - 1; i > -1; i--) {
						if (null == array[i]) {
							return i;
						}
					}
				} else {
					for (int i = array.length - 1; i > -1; i--) {
						if (o.equals(array[i])) {
							return i;
						}
					}
				}
				return -1;
			}
			
			@Override
			public boolean removeAll(Collection<?> c) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean retainAll(Collection<?> c) {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	/**
	 * 返回 map 中包含的值的 List 视图。map 之后的改变不会反映到返回的 List 中。
	 * 
	 * @param map map。
	 * @return map 中包含的值的 List 视图。
	 */
	public static <T> List<T> values(Map<?, T> map) {
		if (map == null) {
			return null;
		}
		List<T> list = new ArrayList<T>(map.size());
		for (T t : map.values()) {
			list.add(t);
		}
		return list;
	}
	
	/**
	 * 返回 map 中包含的建的 List 视图。map 之后的改变不会反映到返回的 List 中。
	 * 
	 * @param map map。
	 * @return map 中包含的建的 List 视图。
	 */
	public static <T> List<T> keys(Map<T, ?> map) {
		if (map == null) {
			return null;
		}
		List<T> list = new ArrayList<T>(map.size());
		for (T t : map.keySet()) {
			list.add(t);
		}
		return list;
	}
	
	/**
	 * 得到 element 在 elements 中出现的次数。
	 * 
	 * @param element 元素。
	 * @param elements element 元素的 {@link Iterable} 。
	 * @return element 在 elements 中出现的次数，如果 elements 为 null ，则返回 0 。
	 */
	public static <E> int countOccurrence(E element, Iterable<E> elements) {
		if (elements == null) {
			return 0;
		}
		int i = 0;
		if (element == null) {
			for (E e : elements) {
				if (null == e) {
					i++;
				}
			}
		} else {
			for (E e : elements) {
				if (element.equals(e)) {
					i++;
				}
			}
		}
		return i;
	}
	
	/**
	 * 将一个集合转换为数组形式，该方法返回的数组为该 {@link Collection} 的迭代器相同规则的数组。
	 * 
	 * @param <E> 其中的元素。
	 * @param collection 要转换的集合。
	 * @param typeClass 要转换到的数组的元素类型。
	 * @return collection 参数集合所对应的数组形式。
	 * @see Collection#iterator()
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] toArray(Collection<E> collection, Class<E> typeClass) {
		E[] t = (E[]) Array.newInstance(typeClass, collection.size());
		int i = 0;
		for (Iterator<E> iterator = collection.iterator(); iterator.hasNext(); i++) {
			t[i] = iterator.next();
		}
		return t;
	}
	



	/**
	 * 返回一个指定 enumeration 上的 {@link Iterator}。该迭代器没有指向的集合，所以对该迭代器进行修改操作会抛出
	 * UnsupportedOperationException。
	 * 
	 * @param enumeration 将为其返回一个 enumeration 的 {@link Iterator}。
	 * @return 指定 enumeration 的 {@link Iterator}。
	 */
	public static <E> Iterator<E> iterator(final Enumeration<E> enumeration) {
		return new Iterator<E>() {
			
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}
			
			public E next() {
				return enumeration.nextElement();
			}
			
			public void remove() {
				throw new UnsupportedOperationException("该迭代器没有指向的集合，所以移除元素为不支持的操作。");
			}
			
		};
	}
	
	/**
	 * 返回一个指定 iterator 上的 {@link Enumeration}。
	 * 
	 * @param iterator 将为其返回一个 {@link Enumeration} 的 iterator。
	 * @return 指定 iterator 的 {@link Enumeration}。
	 */
	public static <E> Enumeration<E> enumeration(final Iterator<E> iterator) {
		
		return new Enumeration<E>() {
			
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}
			
			public E nextElement() {
				return iterator.next();
			}
		};
	}
	
	/**
	 * 将指定元素添加到指定 collection 中。如果为数组或者{@link Collection}或者{@link Iterator}
	 * 的子类则分别添加数组的每个元素，否则直接添加。
	 * 
	 * @param collection value 所要插入的 collection。
	 * @param value 插入 collection 的元素。
	 * @throws ClassCastException 如果 value 的类型和 collection 持有类型不兼容。
	 */
	@SuppressWarnings("unchecked")
	public static <T> void add(Collection<T> collection, Object value) {
		if (value == null) {
			collection.add(null);
		} else {
			// 数组
			if (value.getClass().isArray()) {
				if (value instanceof Object[]) {
					for (Object o : (Object[]) value) {
						collection.add((T) o);
					}
				} else {
					int length = Array.getLength(value);
					for (int i = 0; i < length; i++) {
						collection.add((T) Array.get(value, i));
					}
				}
			} else { // 非数组
				if (value instanceof Collection<?> || value instanceof Stack<?>) {
					for (Object object : (Iterable<T>) value) {
						collection.add((T) object);
					}
				} else if (value instanceof Iterator<?>) {
					// 迭代器
					for (Iterator<T> iterator = (Iterator<T>) value; iterator.hasNext();) {
						collection.add(iterator.next());
					}
				} else {
					collection.add((T) value);
				}
			}
		}
	}
	
	/**
	 * 将所有指定元素添加到指定 stack 中。如果为数组或者{@link Collection}的子类则分别添加数组的每个元素，否则直接添加。
	 * 
	 * @param stack value 所要插入的 Stack。
	 * @param value 推入 stack 的元素。
	 * @throws ClassCastException 如果 value 的类型和 stack 持有类型不兼容。
	 */
	@SuppressWarnings("unchecked")
	public static <T> void push(Stack<T> stack, Object value) {
		if (value == null) {
			stack.push(null);
		} else {
			// 数组
			if (value.getClass().isArray()) {
				if (value instanceof Object[]) {
					for (Object o : (Object[]) value) {
						stack.push((T) o);
					}
				} else {
					int length = Array.getLength(value);
					for (int i = 0; i < length; i++) {
						stack.push((T) Array.get(value, i));
					}
				}
			} else { // 非数组
				if (value instanceof Collection || value instanceof Stack) {
					for (Object object : (Iterable<? super T>) value) {
						stack.push((T) object);
					}
				} else {
					stack.push((T) value);
				}
			}
		}
	}
	
	/**
	 * 同步包装的 Collection。
	 * 
	 * @author Agreal·Lee (e-mail:lixiang@yiji.com)
	 * 
	 */
	static class SynchronizedCollection<E> implements Collection<E>, Serializable {
		
		/** 版本号 */
		private static final long serialVersionUID = -7540724106974451779L;
		
		final Collection<E> collection;
		
		final Object mutex;
		
		SynchronizedCollection(Collection<E> collection) {
			if (collection == null) {
				throw new IllegalArgumentException("The input argument is null!");
			}
			this.collection = collection;
			mutex = this;
		}
		
		SynchronizedCollection(Collection<E> collection, Object mutex) {
			if (collection == null) {
				throw new IllegalArgumentException("The input argument is null!");
			}
			this.collection = collection;
			this.mutex = mutex;
		}
		
		public int size() {
			synchronized (this.mutex) {
				return this.collection.size();
			}
		}
		
		public boolean isEmpty() {
			synchronized (this.mutex) {
				return this.collection.isEmpty();
			}
		}
		
		public boolean contains(Object o) {
			synchronized (this.mutex) {
				return this.collection.contains(o);
			}
		}
		
		public Object[] toArray() {
			synchronized (this.mutex) {
				return this.collection.toArray();
			}
		}
		
		public <T> T[] toArray(T[] a) {
			synchronized (this.mutex) {
				return this.collection.toArray(a);
			}
		}
		
		public Iterator<E> iterator() {
			return this.collection.iterator();
		}
		
		public boolean add(E o) {
			synchronized (this.mutex) {
				return this.collection.add(o);
			}
		}
		
		public boolean remove(Object o) {
			synchronized (this.mutex) {
				return this.collection.remove(o);
			}
		}
		
		public boolean containsAll(Collection<?> coll) {
			synchronized (this.mutex) {
				return this.collection.containsAll(coll);
			}
		}
		
		public boolean addAll(Collection<? extends E> coll) {
			synchronized (this.mutex) {
				return this.collection.addAll(coll);
			}
		}
		
		public boolean removeAll(Collection<?> coll) {
			synchronized (this.mutex) {
				return this.collection.removeAll(coll);
			}
		}
		
		public boolean retainAll(Collection<?> coll) {
			synchronized (this.mutex) {
				return this.collection.retainAll(coll);
			}
		}
		
		public void clear() {
			synchronized (this.mutex) {
				this.collection.clear();
			}
		}
		
		@Override
		public String toString() {
			synchronized (this.mutex) {
				return this.collection.toString();
			}
		}
		
		private void writeObject(ObjectOutputStream s) throws IOException {
			synchronized (this.mutex) {
				s.defaultWriteObject();
			}
		}
	}
	
	/**
	 * 同步包装的 Set。
	 * 
	 * @author Agreal·Lee (e-mail:lixiang@yiji.com)
	 * 
	 */
	static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
		
		/** 版本号 */
		private static final long serialVersionUID = 6982504952424781802L;
		
		SynchronizedSet(Set<E> set) {
			super(set);
		}
		
		SynchronizedSet(Set<E> set, Object mutex) {
			super(set, mutex);
		}
		
		@Override
		public boolean equals(Object o) {
			synchronized (this.mutex) {
				return this.collection.equals(o);
			}
		}
		
		@Override
		public int hashCode() {
			synchronized (this.mutex) {
				return this.collection.hashCode();
			}
		}
	}

	/**
	 * 判断Collection是否为空。
	 * 
	 * @param collection 要判断的Collection。
	 * @return 如果Collection为 null 或者 {@link Collection#isEmpty()} 返回true时，则返回
	 * true。
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}
	
	/**
	 * 判断Stack是否为空。
	 * 
	 * @param stack 要判断的Stack。
	 * @return 如果Stack为 null 或者 {@link Stack#isEmpty()} 返回true时，则返回 true。
	 */
	public static boolean isEmpty(Stack<?> stack) {
		return (stack == null || stack.isEmpty());
	}
	
	/**
	 * 判断Map是否为空。
	 * 
	 * @param map 要判断的Map。
	 * @return 如果Map为 null 或者 {@link Map#isEmpty()} 返回true时，则返回 true。
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}
	
	/**
	 * 判断Collection是否不为空。
	 * 
	 * @param collection 要判断的Collection。
	 * @return 如果Collection不为 null 且 {@link Collection#isEmpty()} 返回false时，则返回
	 * true。
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return (collection != null && !collection.isEmpty());
	}
	
	/**
	 * 判断Stack是否不为空。
	 * 
	 * @param stack 要判断的Stack。
	 * @return 如果Stack不为 null 且 {@link Stack#isEmpty()} 返回false时，则返回 true。
	 */
	public static boolean isNotEmpty(Stack<?> stack) {
		return (stack != null && !stack.isEmpty());
	}
	
	/**
	 * 判断Map是否不为空。
	 * 
	 * @param map 要判断的Map。
	 * @return 如果Map不为 null 且 {@link Map#isEmpty()} 返回false时，则返回 true。
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return (map != null && !map.isEmpty());
	}

	/**
	 * 将 collection 转换为 字符串数组。
	 * 
	 * @param collection 需要转换的 collection 。
	 * @return collection 对应的字符串数组。
	 * @see StringUtils#toString(Object)
	 */
	public static String[] toStringArray(Collection<?> collection) {
		if (collection == null) {
			return null;
		}
		String[] stringArray = new String[collection.size()];
		int i = 0;
		for (Object o : collection) {
			stringArray[i] = StringUtils.toString(o);
			i++;
		}
		return stringArray;
	}
	


	/**
	 * 如果 paramMap 中的键已经不存在于 map 中，则将它 put 到 map 中。
	 * <p>
	 * 如果 map 或者 paramMap 为 null ，则不会有任何变化。
	 * 
	 * @param map 一个 Map 。
	 * @param paramMap 一个 Map 。
	 */
	public static <K, V> void putAllIfAbsent(Map<K, V> map, Map<? extends K, ? extends V> paramMap) {
		if (map == null || paramMap == null) {
			return;
		}
		for (Entry<? extends K, ? extends V> entry : paramMap.entrySet()) {
			if (!map.containsKey(entry.getKey())) {
				map.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * 计算 size 指定的大小的使用默认加载因子（0.75f）的 {@link HashMap} 的最小初始容量。
	 * 
	 * @param size 指定的大小。
	 * @return size 指定的大小的使用默认加载因子（0.75f）的 {@link HashMap} 的最小初始容量，如果该值小于 16 ，则为
	 * 16。
	 */
	public static int calculateHashMapMinInitialCapacity(int size) {
		return calculateHashMapMinInitialCapacity(size, HASH_MAP_DEFAULT_LOAD_FACTOR, HASH_MAP_DEFAULT_INITIAL_CAPACITY);
	}
	
	/**
	 * 计算 size 指定的大小的使用 loadFactor 指定的加载因子的 {@link HashMap} 的最小初始容量。
	 * 
	 * @param size 指定的大小。
	 * @param loadFactor 指定的加载因子。
	 * @param initlalCapcity 容器默认初始化大小。
	 * @return size 指定的大小的使用 loadFactor 指定的加载因子的 {@link HashMap} 的最小初始容量，如果该值小于
	 * initlalCapcity ，则为 initlalCapcity。
	 */
	public static int calculateHashMapMinInitialCapacity(int size, float loadFactor, int initlalCapcity) {
		return Math.max((int) (size / loadFactor) + 1, initlalCapcity);
	}
	

}
