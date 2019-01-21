package com.oop.scala.files

import scala.annotation.tailrec

class Directory(override val parentPath: String,
                override val name: String,
                val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

  override def asFile: File = throw new FileSystemException("Specified dirEntry is a directory not a file")

  def replaceEntry(entryName: String, newEntry: DirEntry): Directory = {
    new Directory(parentPath, name, contents.filter(a => !a.name.equals(entryName)) :+ newEntry)
  }

  def addEntry(newEntry: DirEntry): Directory = {
    new Directory(parentPath, name, contents :+ newEntry)
  }

  def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendant(path.tail)

  }

  def getAllFoldersInPath: List[String] = {
    path.substring(1).split(Directory.SEPARATOR).toList.filter(x => !x.isEmpty)
  }

  def hasEntry(name: String): Boolean = findEntry(name) != null

  def findEntry(entryName: String): DirEntry = {
    @tailrec
    def findEntryHelper(name: String, contentList: List[DirEntry]): DirEntry =
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)

    findEntryHelper(entryName, contents)
  }

  override def asDirectory: Directory = this

  override def getType: String = "Directory"
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")

  def empty(parentPath: String, name: String): Directory = {
    new Directory(parentPath, name, List())
  }
}
