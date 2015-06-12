// DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
//
// Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
//
// Oracle and Java are registered trademarks of Oracle and/or its affiliates.
// Other names may be trademarks of their respective owners.
//
// The contents of this file are subject to the terms of either the GNU
// General Public License Version 2 only ("GPL") or the Common
// Development and Distribution License("CDDL") (collectively, the
// "License"). You may not use this file except in compliance with the
// License. You can obtain a copy of the License at
// http://www.netbeans.org/cddl-gplv2.html
// or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
// specific language governing permissions and limitations under the
// License.  When distributing the software, include this License Header
// Notice in each file and include the License file at
// nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
// particular file as subject to the "Classpath" exception as provided
// by Oracle in the GPL Version 2 section of the License file that
// accompanied this code. If applicable, add the following below the
// License Header, with the fields enclosed by brackets [] replaced by
// your own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Contributor(s):
// 
// The Original Software is the Nokia Deployment.                      
// The Initial Developer of the Original Software is Nokia Corporation.
// Portions created by Nokia Corporation Copyright 2005, 2007.         
// All Rights Reserved.  
//                                              
// If you wish your version of this file to be governed by only the CDDL
// or only the GPL Version 2, indicate your decision by adding
// "[Contributor] elects to include this software in this distribution
// under the [CDDL or GPL Version 2] license." If you do not indicate a
// single choice of license, a recipient has the option to distribute
// your version of this file under either the CDDL, the GPL Version 2 or
// to extend the choice of license to its licensees as provided above.
// However, if you add GPL Version 2 code and therefore, elected the GPL
// Version 2 license, then the option applies only if the new code is
// made subject to such option by the copyright holder.

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_nokia_phone_deploy_CONA */

#ifndef _Included_com_nokia_phone_deploy_CONA
#define _Included_com_nokia_phone_deploy_CONA
#ifdef __cplusplus
extern "C" {
#endif
#undef com_nokia_phone_deploy_CONA_CONAPI_MEDIA_ALL
#define com_nokia_phone_deploy_CONA_CONAPI_MEDIA_ALL 1L
#undef com_nokia_phone_deploy_CONA_CONAPI_MEDIA_IRDA
#define com_nokia_phone_deploy_CONA_CONAPI_MEDIA_IRDA 2L
#undef com_nokia_phone_deploy_CONA_CONAPI_MEDIA_SERIAL
#define com_nokia_phone_deploy_CONA_CONAPI_MEDIA_SERIAL 4L
#undef com_nokia_phone_deploy_CONA_CONAPI_MEDIA_BLUETOOTH
#define com_nokia_phone_deploy_CONA_CONAPI_MEDIA_BLUETOOTH 8L
#undef com_nokia_phone_deploy_CONA_CONAPI_MEDIA_USB
#define com_nokia_phone_deploy_CONA_CONAPI_MEDIA_USB 16L
#undef com_nokia_phone_deploy_CONA_CONA_APPLICATION_TYPE_SIS
#define com_nokia_phone_deploy_CONA_CONA_APPLICATION_TYPE_SIS 1L
#undef com_nokia_phone_deploy_CONA_CONA_APPLICATION_TYPE_JAVA
#define com_nokia_phone_deploy_CONA_CONA_APPLICATION_TYPE_JAVA 2L
#undef com_nokia_phone_deploy_CONA_CONARefreshDeviceMemoryValues
#define com_nokia_phone_deploy_CONA_CONARefreshDeviceMemoryValues 1L
#undef com_nokia_phone_deploy_CONA_CONASetCurrentFolder
#define com_nokia_phone_deploy_CONA_CONASetCurrentFolder 2L
#undef com_nokia_phone_deploy_CONA_CONAFindBegin
#define com_nokia_phone_deploy_CONA_CONAFindBegin 4L
#undef com_nokia_phone_deploy_CONA_CONACreateFolder
#define com_nokia_phone_deploy_CONA_CONACreateFolder 8L
#undef com_nokia_phone_deploy_CONA_CONADeleteFolder
#define com_nokia_phone_deploy_CONA_CONADeleteFolder 16L
#undef com_nokia_phone_deploy_CONA_CONARenameFolder
#define com_nokia_phone_deploy_CONA_CONARenameFolder 32L
#undef com_nokia_phone_deploy_CONA_CONAGetFileInfo
#define com_nokia_phone_deploy_CONA_CONAGetFileInfo 64L
#undef com_nokia_phone_deploy_CONA_CONADeleteFile
#define com_nokia_phone_deploy_CONA_CONADeleteFile 128L
#undef com_nokia_phone_deploy_CONA_CONAMoveFile
#define com_nokia_phone_deploy_CONA_CONAMoveFile 256L
#undef com_nokia_phone_deploy_CONA_CONACopyFile
#define com_nokia_phone_deploy_CONA_CONACopyFile 512L
#undef com_nokia_phone_deploy_CONA_CONARenameFile
#define com_nokia_phone_deploy_CONA_CONARenameFile 1024L
#undef com_nokia_phone_deploy_CONA_CONAReadFile
#define com_nokia_phone_deploy_CONA_CONAReadFile 2048L
#undef com_nokia_phone_deploy_CONA_CONAWriteFile
#define com_nokia_phone_deploy_CONA_CONAWriteFile 4096L
#undef com_nokia_phone_deploy_CONA_CONAConnectionLost
#define com_nokia_phone_deploy_CONA_CONAConnectionLost 8192L
#undef com_nokia_phone_deploy_CONA_CONAInstallApplication
#define com_nokia_phone_deploy_CONA_CONAInstallApplication 16384L
/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_getVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_nokia_phone_deploy_CONA_native_1getVersion
  (JNIEnv *, jobject);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_connectServiceLayer
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_phone_deploy_CONA_native_1connectServiceLayer
  (JNIEnv *, jobject);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_updateDeviceList
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_phone_deploy_CONA_native_1updateDeviceList
  (JNIEnv *, jobject);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_getDeviceType
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_nokia_phone_deploy_CONA_native_1getDeviceType
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_getDevices
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_nokia_phone_deploy_CONA_native_1getDevices
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_openConnectionTo
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_phone_deploy_CONA_native_1openConnectionTo
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_installFile
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_phone_deploy_CONA_native_1installFile
  (JNIEnv *, jobject, jstring, jstring, jstring, jint, jboolean);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_setCurrentFolder
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_phone_deploy_CONA_native_1setCurrentFolder
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_createFolder
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_phone_deploy_CONA_native_1createFolder
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_putFile
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_phone_deploy_CONA_native_1putFile
  (JNIEnv *, jobject, jstring, jstring, jstring);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_getStatus
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_nokia_phone_deploy_CONA_native_1getStatus
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_closeConnection
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_phone_deploy_CONA_native_1closeConnection
  (JNIEnv *, jobject);

/*
 * Class:     com_nokia_phone_deploy_CONA
 * Method:    native_disconnectServiceLayer
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_phone_deploy_CONA_native_1disconnectServiceLayer
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif