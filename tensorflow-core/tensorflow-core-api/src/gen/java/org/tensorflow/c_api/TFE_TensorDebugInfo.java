// Targeted by JavaCPP version 1.5.1: DO NOT EDIT THIS FILE

package org.tensorflow.c_api;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static org.tensorflow.c_api.global.tensorflow.*;


// Debugging/Profiling information for TFE_TensorHandle
//
// TFE_TensorDebugInfo contains information useful for debugging and
// profiling tensors.
@Opaque @Properties(inherit = org.tensorflow.c_api.presets.tensorflow.class)
public class TFE_TensorDebugInfo extends Pointer {
    /** Empty constructor. Calls {@code super((Pointer)null)}. */
    public TFE_TensorDebugInfo() { super((Pointer)null); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public TFE_TensorDebugInfo(Pointer p) { super(p); }
}
