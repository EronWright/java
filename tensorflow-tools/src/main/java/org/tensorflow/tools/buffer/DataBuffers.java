/*
 Copyright 2019 The TensorFlow Authors. All Rights Reserved.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 =======================================================================
 */
package org.tensorflow.tools.buffer;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.BitSet;
import org.tensorflow.tools.buffer.impl.Validator;
import org.tensorflow.tools.buffer.impl.adapter.DataBufferAdapterFactory;
import org.tensorflow.tools.buffer.impl.misc.MiscDataBufferFactory;
import org.tensorflow.tools.buffer.impl.nio.NioDataBufferFactory;
import org.tensorflow.tools.buffer.impl.raw.RawDataBufferFactory;
import org.tensorflow.tools.buffer.layout.BooleanDataLayout;
import org.tensorflow.tools.buffer.layout.DataLayout;
import org.tensorflow.tools.buffer.layout.DoubleDataLayout;
import org.tensorflow.tools.buffer.layout.FloatDataLayout;
import org.tensorflow.tools.buffer.layout.IntDataLayout;
import org.tensorflow.tools.buffer.layout.LongDataLayout;
import org.tensorflow.tools.buffer.layout.ShortDataLayout;

/**
 * Helper class for creating {@code DataBuffer} instances.
 */
public final class DataBuffers {

  /**
   * Creates a buffer of bytes that can store up to {@code size} values
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static ByteDataBuffer ofBytes(long size) {
    Validator.createArgs(size, MAX_32BITS);
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(new byte[(int)size], false);
    }
    return NioDataBufferFactory.create(ByteBuffer.allocate((int)size));
  }

  /**
   * Creates a buffer of longs that can store up to {@code size} values
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static LongDataBuffer ofLongs(long size) {
    Validator.createArgs(size, MAX_32BITS);
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(new long[(int)size], false);
    }
    return NioDataBufferFactory.create(LongBuffer.allocate((int)size));
  }

  /**
   * Creates a buffer of integers that can store up to {@code size} values
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static IntDataBuffer ofInts(long size) {
    Validator.createArgs(size, MAX_32BITS);
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(new int[(int)size], false);
    }
    return NioDataBufferFactory.create(IntBuffer.allocate((int)size));
  }

  /**
   * Creates a buffer of shorts that can store up to {@code size} values
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static ShortDataBuffer ofShorts(long size) {
    Validator.createArgs(size, MAX_32BITS);
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(new short[(int)size], false);
    }
    return NioDataBufferFactory.create(ShortBuffer.allocate((int)size));
  }

  /**
   * Creates a buffer of doubles that can store up to {@code size} values
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static DoubleDataBuffer ofDoubles(long size) {
    Validator.createArgs(size, MAX_32BITS);
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(new double[(int)size], false);
    }
    return NioDataBufferFactory.create(DoubleBuffer.allocate((int)size));
  }

  /**
   * Creates a buffer of floats that can store up to {@code size} values
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static FloatDataBuffer ofFloats(long size) {
    Validator.createArgs(size, MAX_32BITS);
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(new float[(int)size], false);
    }
    return NioDataBufferFactory.create(FloatBuffer.allocate((int)size));
  }

  /**
   * Creates a buffer of booleans that can store up to {@code size} values
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static BooleanDataBuffer ofBooleans(long size) {
    Validator.createArgs(size, MAX_32BITS);
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(new boolean[(int)size], false);
    }
    return MiscDataBufferFactory.create(new BitSet((int)size), size, false);
  }

  /**
   * Creates a buffer of references to objects of type {@code clazz` that can store up to `size}
   * values.
   *
   * @param type the type of object stored in this buffer
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static <T> DataBuffer<T> ofObjects(Class<T> type, long size) {
    Validator.createArgs(size, MAX_32BITS);
    @SuppressWarnings("unchecked")
    T[] array = (T[])Array.newInstance(type, (int)size);
    return MiscDataBufferFactory.create(array, false);
  }

  /**
   * Creates a virtual buffer of longs that can store up to {@code size} values.
   *
   * <p>The provided layout is used to create the long values to/from bytes, allowing custom
   * representation of a long.
   *
   * @param size size of the buffer to allocate
   * @param layout an object converting buffer data to longs
   * @return a new buffer
   */
  public static LongDataBuffer ofLongs(long size, LongDataLayout layout) {
    Validator.createArgs(size, MAX_32BITS);
    return toLongs(ofBytes(size * layout.sizeInBytes()), layout);
  }

  /**
   * Creates a virtual buffer of integers that can store up to {@code size} values.
   *
   * <p>The provided layout is used to create the integer values to/from bytes, allowing custom
   * representation of an integer.
   *
   * @param size size of the buffer to allocate
   * @param layout an object converting buffer data to integers
   * @return a new buffer
   */
  public static IntDataBuffer ofInts(long size, IntDataLayout layout) {
    Validator.createArgs(size, MAX_32BITS);
    return toInts(ofBytes(size * layout.sizeInBytes()), layout);
  }

  /**
   * Creates a virtual buffer of shorts that can store up to {@code size} values.
   *
   * <p>The provided layout is used to create the short values to/from bytes, allowing custom
   * representation of a short.
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static ShortDataBuffer ofShorts(long size, ShortDataLayout layout) {
    Validator.createArgs(size, MAX_32BITS);
    return toShorts(ofBytes(size * layout.sizeInBytes()), layout);
  }

  /**
   * Creates a virtual buffer of doubles that can store up to {@code size} values.
   *
   * <p>The provided layout is used to create the double values to/from bytes, allowing custom
   * representation of a double.
   *
   * @param size size of the buffer to allocate
   * @param layout an object converting buffer data to doubles
   * @return a new buffer
   */
  public static DoubleDataBuffer ofDoubles(long size, DoubleDataLayout layout) {
    Validator.createArgs(size, MAX_32BITS);
    return toDoubles(ofBytes(size * layout.sizeInBytes()), layout);
  }

  /**
   * Creates a virtual buffer of floats that can store up to {@code size} values.
   *
   * <p>The provided layout is used to create the float values to/from bytes, allowing custom
   * representation of a float.
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static FloatDataBuffer ofFloats(long size, FloatDataLayout layout) {
    Validator.createArgs(size, MAX_32BITS);
    return toFloats(ofBytes(size * layout.sizeInBytes()), layout);
  }

  /**
   * Creates a virtual buffer of booleans that can store up to {@code size} values.
   *
   * <p>The provided layout is used to create the boolean values to/from bytes, allowing custom
   * representation of a boolean.
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static BooleanDataBuffer ofBooleans(long size, BooleanDataLayout layout) {
    Validator.createArgs(size, MAX_32BITS);
    return toBooleans(ofBytes(size * layout.sizeInBytes()), layout);
  }

  /**
   * Creates a virtual buffer that can store up to {@code size} values.
   *
   * <p>The provided layout is used to create the values to/from bytes, allowing custom
   * representation of this buffer type.
   *
   * @param size size of the buffer to allocate
   * @return a new buffer
   */
  public static <T> DataBuffer<T> ofObjects(long size, DataLayout<T> layout) {
    Validator.createArgs(size, MAX_32BITS);
    return toObjects(ofBytes(size * layout.sizeInBytes()), layout);
  }

  /**
   * Adapt a physical buffer to a virtual buffer of longs.
   *
   * <p>The provided layout is used to create the long values to/from bytes, allowing custom
   * representation of a long integer.
   *
   * @param buffer the buffer to adapt
   * @param layout an object converting buffer data to integers
   * @return a new buffer
   */
  public static LongDataBuffer toLongs(ByteDataBuffer buffer, LongDataLayout layout) {
    return DataBufferAdapterFactory.create(buffer, layout);
  }

  /**
   * Adapt a physical buffer to a virtual buffer of integers.
   *
   * <p>The provided layout is used to create the integer values to/from bytes, allowing custom
   * representation of a integer.
   *
   * @param buffer the buffer to adapt
   * @param layout an object converting buffer data to integers
   * @return a new buffer
   */
  public static IntDataBuffer toInts(ByteDataBuffer buffer, IntDataLayout layout) {
    return DataBufferAdapterFactory.create(buffer, layout);
  }

  /**
   * Adapt a physical buffer to a virtual buffer of shorts.
   *
   * <p>The provided layout is used to create the short values to/from bytes, allowing custom
   * representation of a short.
   *
   * @param buffer the buffer to adapt
   * @param layout an object converting buffer data to shorts
   * @return a new buffer
   */
  public static ShortDataBuffer toShorts(ByteDataBuffer buffer, ShortDataLayout layout) {
    return DataBufferAdapterFactory.create(buffer, layout);
  }

  /**
   * Adapt a physical buffer to a virtual buffer of doubles.
   *
   * <p>The provided layout is used to create the double values to/from bytes, allowing custom
   * representation of a double.
   *
   * @param buffer the buffer to adapt
   * @param layout an object converting buffer data to doubles
   * @return a new buffer
   */
  public static DoubleDataBuffer toDoubles(ByteDataBuffer buffer, DoubleDataLayout layout) {
    return DataBufferAdapterFactory.create(buffer, layout);
  }

  /**
   * Adapt a physical buffer to a virtual buffer of floats.
   *
   * <p>The provided layout is used to create the float values to/from bytes, allowing custom
   * representation of a float.
   *
   * @param buffer the buffer to adapt
   * @param layout an object converting buffer data to floats
   * @return a new buffer
   */
  public static FloatDataBuffer toFloats(ByteDataBuffer buffer, FloatDataLayout layout) {
    return DataBufferAdapterFactory.create(buffer, layout);
  }

  /**
   * Adapt a physical buffer to a virtual buffer of booleans.
   *
   * <p>The provided layout is used to create the boolean values to/from bytes, allowing custom
   * representation of a boolean.
   *
   * @param buffer the buffer to adapt
   * @param layout an object converting buffer data to booleans
   * @return a new buffer
   */
  public static BooleanDataBuffer toBooleans(ByteDataBuffer buffer, BooleanDataLayout layout) {
    return DataBufferAdapterFactory.create(buffer, layout);
  }

  /**
   * Adapt a physical buffer to a virtual buffer.
   *
   * <p>The provided layout is used to create the values to/from bytes, allowing custom
   * representation of this buffer type.
   *
   * @param buffer the buffer to adapt
   * @param layout an object converting buffer data to booleans
   * @return a new buffer
   */
  public static <T> DataBuffer<T> toObjects(ByteDataBuffer buffer, DataLayout<T> layout) {
    return DataBufferAdapterFactory.create(buffer, layout);
  }

  /**
   * Create a buffer from an array of floats into a data buffer.
   *
   * @param array array of floats
   * @param readOnly true if the buffer created must be read-only
   * @param makeCopy true if the array must be copied, false will wrap the provided array
   * @return a new buffer
   */
  public static FloatDataBuffer from(float[] array, boolean readOnly, boolean makeCopy) {
    float[] bufferArray = makeCopy ? Arrays.copyOf(array, array.length) : array;
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(bufferArray, readOnly);
    }
    FloatBuffer buf = FloatBuffer.wrap(bufferArray);
    return NioDataBufferFactory.create(readOnly ? buf.asReadOnlyBuffer() : buf);
  }

  /**
   * Create a buffer from an array of bytes into a data buffer.
   *
   * @param array array of bytes
   * @param readOnly true if the buffer created must be read-only
   * @param makeCopy true if the array must be copied, false will wrap the provided array
   * @return a new buffer
   */
  public static ByteDataBuffer from(byte[] array, boolean readOnly, boolean makeCopy) {
    byte[] bufferArray = makeCopy ? Arrays.copyOf(array, array.length) : array;
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(bufferArray, readOnly);
    }
    ByteBuffer buf = ByteBuffer.wrap(bufferArray);
    return NioDataBufferFactory.create(readOnly ? buf.asReadOnlyBuffer() : buf);
  }

  /**
   * Create a buffer from an array of longs into a data buffer.
   *
   * @param array array of longs
   * @param readOnly true if the buffer created must be read-only
   * @param makeCopy true if the array must be copied, false will wrap the provided array
   * @return a new buffer
   */
  public static LongDataBuffer from(long[] array, boolean readOnly, boolean makeCopy) {
    long[] bufferArray = makeCopy ? Arrays.copyOf(array, array.length) : array;
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(bufferArray, readOnly);
    }
    LongBuffer buf = LongBuffer.wrap(bufferArray);
    return NioDataBufferFactory.create(readOnly ? buf.asReadOnlyBuffer() : buf);
  }

  /**
   * Create a buffer from an array of ints into a data buffer.
   *
   * @param array array of ints
   * @param readOnly true if the buffer created must be read-only
   * @param makeCopy true if the array must be copied, false will wrap the provided array
   * @return a new buffer
   */
  public static IntDataBuffer from(int[] array, boolean readOnly, boolean makeCopy) {
    int[] bufferArray = makeCopy ? Arrays.copyOf(array, array.length) : array;
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(bufferArray, readOnly);
    }
    IntBuffer buf = IntBuffer.wrap(bufferArray);
    return NioDataBufferFactory.create(readOnly ? buf.asReadOnlyBuffer() : buf);
  }

  /**
   * Create a buffer from an array of shorts into a data buffer.
   *
   * @param array array of shorts
   * @param readOnly true if the buffer created must be read-only
   * @param makeCopy true if the array must be copied, false will wrap the provided array
   * @return a new buffer
   */
  public static ShortDataBuffer from(short[] array, boolean readOnly, boolean makeCopy) {
    short[] bufferArray = makeCopy ? Arrays.copyOf(array, array.length) : array;
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(bufferArray, readOnly);
    }
    ShortBuffer buf = ShortBuffer.wrap(bufferArray);
    return NioDataBufferFactory.create(readOnly ? buf.asReadOnlyBuffer() : buf);
  }

  /**
   * Create a buffer from an array of doubles into a data buffer.
   *
   * @param array array of doubles
   * @param readOnly true if the buffer created must be read-only
   * @param makeCopy true if the array must be copied, false will wrap the provided array
   * @return a new buffer
   */
  public static DoubleDataBuffer from(double[] array, boolean readOnly, boolean makeCopy) {
    double[] bufferArray = makeCopy ? Arrays.copyOf(array, array.length) : array;
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(bufferArray, readOnly);
    }
    DoubleBuffer buf = DoubleBuffer.wrap(bufferArray);
    return NioDataBufferFactory.create(readOnly ? buf.asReadOnlyBuffer() : buf);
  }

  /**
   * Create a buffer from an array of booleans into a data buffer.
   *
   * @param array array of booleans
   * @param readOnly true if the buffer created must be read-only
   * @param makeCopy true if the array must be copied, false will wrap the provided array
   * @return a new buffer
   */
  public static BooleanDataBuffer from(boolean[] array, boolean readOnly, boolean makeCopy) {
    boolean[] bufferArray = makeCopy ? Arrays.copyOf(array, array.length) : array;
    if (RawDataBufferFactory.canBeUsed()) {
      return RawDataBufferFactory.create(bufferArray, readOnly);
    }
    return MiscDataBufferFactory.create(bufferArray, readOnly);
  }

  /**
   * Create a buffer from an array of objects into a data buffer.
   *
   * @param array array of objects
   * @param readOnly true if the buffer created must be read-only
   * @param makeCopy true if the array must be copied, false will wrap the provided array
   * @return a new buffer
   */
  public static <T> DataBuffer<T> from(T[] array, boolean readOnly, boolean makeCopy) {
    T[] bufferArray = makeCopy ? Arrays.copyOf(array, array.length) : array;
    return MiscDataBufferFactory.create(bufferArray, readOnly);
  }

  /**
   * Wraps a JDK NIO {@link ByteBuffer} into a data buffer.
   *
   * @param buf buffer to wrap
   * @return a new buffer
   */
  public static ByteDataBuffer from(ByteBuffer buf) {
    return NioDataBufferFactory.create(buf.duplicate());
  }

  /**
   * Wraps a JDK NIO {@link IntBuffer} into a data buffer.
   *
   * @param buf buffer to wrap
   * @return a new buffer
   */
  public static IntDataBuffer from(IntBuffer buf) {
    return NioDataBufferFactory.create(buf.duplicate());
  }

  /**
   * Wraps a JDK NIO {@link ShortBuffer} into a data buffer.
   *
   * @param buf buffer to wrap
   * @return a new buffer
   */
  public static ShortDataBuffer from(ShortBuffer buf) {
    return NioDataBufferFactory.create(buf.duplicate());
  }

  /**
   * Wraps a JDK NIO {@link LongBuffer} into a data buffer.
   *
   * @param buf buffer to wrap
   * @return a new buffer
   */
  public static LongDataBuffer from(LongBuffer buf) {
    return NioDataBufferFactory.create(buf.duplicate());
  }

  /**
   * Wraps a JDK NIO {@link FloatBuffer} into a data buffer.
   *
   * @param buf buffer to wrap
   * @return a new buffer
   */
  public static FloatDataBuffer from(FloatBuffer buf) {
    return NioDataBufferFactory.create(buf.duplicate());
  }

  /**
   * Wraps a JDK NIO {@link DoubleBuffer} into a data buffer.
   *
   * @param buf buffer to wrap
   * @return a new buffer
   */
  public static DoubleDataBuffer from(DoubleBuffer buf) {
    return NioDataBufferFactory.create(buf.duplicate());
  }

  /*
   * The maximum size for a buffer of this type, i.e. the maximum number of bytes it can store.
   * <p>
   * As the maximum size may vary depending on the JVM implementation and on the platform, this
   * property returns a value that is safe for most of them.
   */
  static long MAX_32BITS = Integer.MAX_VALUE - 10;
}
