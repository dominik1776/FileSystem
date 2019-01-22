package com.oop.scala.files

abstract class DirEntry(val parentPath: String, val name: String) {
  def isDirectory: Boolean

  def isFile: Boolean

  def path: String = {
    parentPath + separatorIfNecessary + name
  }
val separatorIfNecessary = {
  if (Directory.ROOT_PATH.equals(parentPath)) ""
  else Directory.SEPARATOR
}

  def asDirectory: Directory

  def asFile: File

  def getType: String


}
