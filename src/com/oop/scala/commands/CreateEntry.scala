package com.oop.scala.commands
import com.oop.scala.files.{DirEntry, Directory}
import com.oop.scala.filesystem.State

abstract class CreateEntry(name: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Entry " + name + " already exist!")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(name + " must not contain separators!")
    } else if (checkillegal(name)) {
      state.setMessage(name + ": illegal entry name!")
    } else {
      doCreateEntry(name, state)
    }
  }

  def checkillegal(name: String): Boolean = {
    name.contains(".")
  }

  def createSpecyficEntry(state: State): DirEntry

  def doCreateEntry(name: String, state: State): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val wd = state.wd
    //get all directories in fullpath
    val allDirsInPath: List[String] = wd.getAllFoldersInPath
    //create new directory in wd
    val newSpecificEntry = createSpecyficEntry(state)
    //update all hierarhy start from root
    val newRoot = updateStructure(state.root, allDirsInPath, newSpecificEntry)
    //find wd in new structure
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }
}
