package com.oop.scala.files

class File(override val parentPath: String, override val name: String, contenst: String) extends DirEntry(parentPath, name) {

  override def asDirectory: Directory = throw new FileSystemException("Specified dirEntry is a file not directory")

  override def asFile: File = this

  override def getType: String = "File"

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true
}

object File {
  def empty(parentPath: String, name: String): File ={
    new File(parentPath, name, "")
  }
}
