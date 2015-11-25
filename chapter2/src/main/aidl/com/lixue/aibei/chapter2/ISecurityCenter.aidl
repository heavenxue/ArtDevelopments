// ISecurityCenter.aidl
package com.lixue.aibei.chapter2;

// Declare any non-default types here with import statements
/**提供加解密功能**/
interface ISecurityCenter {
   String encrypt(String content);
   String decrypt(String password);
}
