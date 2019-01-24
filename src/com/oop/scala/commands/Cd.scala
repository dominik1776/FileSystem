package com.oop.scala.commands
import com.oop.scala.files.{DirEntry, Directory}
import com.oop.scala.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {
  @tailrec
  private def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
    if(path.isEmpty || path.head.isEmpty) currentDirectory
    else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
    else {
      val nextDir = currentDirectory.findEntry(path.head)
      if (nextDir == null || !nextDir.isDirectory ) null
      else findEntryHelper(nextDir.asDirectory, path.tail)
    }
  }

  def colapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
    if (path.isEmpty) result
    else if (".".equals(path.head)) colapseRelativeTokens(path.tail, result)
    else if ((".." ).equals(path.head)){
      if (result.isEmpty) null
      else colapseRelativeTokens(path.tail, result.init)
    }
    else colapseRelativeTokens(path.tail, result :+ path.head)
  }

  def doFindEntry(root: Directory, absolutePath: String): DirEntry = {
    val tokens: List[String] = absolutePath.substring(1).split(Directory.SEPARATOR).toList
    val newToken = colapseRelativeTokens(tokens, List())
      findEntryHelper(root, newToken)

  }


  override def apply(state: State): State = {
    //find root
    val root = state.root
    val wd = state.wd
    //find absolute path
    val absolutePath =
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if(wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir
    //find the directory to cd to
    val destinationDir = doFindEntry(root, absolutePath)
    //do the change the state
    if(destinationDir == null || !destinationDir.isDirectory) state.setMessage(dir + ": no such directory")
    else State(root, destinationDir.asDirectory)
  }
}
