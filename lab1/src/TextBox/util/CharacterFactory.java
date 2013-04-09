package TextBox.util;


import TextBox.dom.Character;

import java.util.HashMap;
import java.util.Map;

public class CharacterFactory {
    protected static Map<Integer, Character> chars = new HashMap<Integer, Character>();
    public static Character newChar(char character) {
        Character resultChar = chars.get((int) character);
        if (resultChar == null) {
            TextBox.dom.Character c = new Character(character);
            chars.put((int) character, c);
            resultChar = c;
        }
        return resultChar;
    }
}
