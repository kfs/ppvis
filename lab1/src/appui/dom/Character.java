package appui.dom;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 18.03.13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class Character extends Glyph {
    private char _character;
    public char getCH() {
        return _character;
    }
    public Character(char _char) {
        _character = _char;
    }
}
