package TextBox.util;


import TextBox.dom.Character;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 30.03.13
 * Time: 16:05
 * To change this template use File | Settings | File Templates.
 */
public class CharacterFactory {
    protected static Map<Integer, Character> chars = new HashMap<Integer, Character>();
    public static TextBox.dom.Character newChar(char character) {
        Character resultChar = chars.get((int) character);
        if (resultChar == null) {
            TextBox.dom.Character c = new TextBox.dom.Character(character);
            chars.put((int) character, c);
            resultChar = c;
        }
        return resultChar;
    }
}
