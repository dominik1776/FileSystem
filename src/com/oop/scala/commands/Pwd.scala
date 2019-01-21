package com.oop.scala.commands
import com.oop.scala.filesystem.State

class Pwd extends Command {
  override def apply(state: State): State = {
    state.setMessage(state.wd.path)
  }
}
