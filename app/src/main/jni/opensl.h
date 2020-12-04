/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <SLES/OpenSLES.h>
/* Header for class com_test_recoder_MainActivity */

#ifndef _Included_com_test_recoder_MainActivity
#define _Included_com_test_recoder_MainActivity
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     com_test_recoder_MainActivity
 * Method:    play
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_test_recoder_MainActivity_play
        (JNIEnv *, jobject, jstring);

/*
 * Class:     com_test_recoder_MainActivity
 * Method:    playStop
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_test_recoder_MainActivity_playStop
        (JNIEnv *, jobject);

/*
 * Class:     com_test_recoder_MainActivity
 * Method:    record
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_test_recoder_MainActivity_record
        (JNIEnv *, jobject, jstring);

/*
 * Class:     com_test_recoder_MainActivity
 * Method:    stopRecod
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_test_recoder_MainActivity_stopRecod
        (JNIEnv *, jobject);

JNIEXPORT jint JNICALL Java_com_test_recoder_MainActivity_setAvailableTime
        (JNIEnv *, jobject, jlong);

JNIEXPORT jlong JNICALL Java_com_test_recoder_MainActivity_getSetTime
        (JNIEnv *, jobject);
#ifdef __cplusplus
}
#endif
#endif