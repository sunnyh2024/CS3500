package cs3500.freecell.controller;

/**
 * represents the section of the input that the controller is currently processing. SOURCE is the
 * card's source pile, INDEX is the card index at that source pile, and DESTINATION is the card's
 * destination pile
 */
public enum ControlState {
  SOURCE, INDEX, DESTINATION;
}
