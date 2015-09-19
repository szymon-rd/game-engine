package pl.jacadev.game.resmanagement.manageables.loaders.staticentitymodel;

import pl.jacadev.game.resmanagement.manageables.loaders.DataFileProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * @author Jaca777
 *         Created 29.01.15 at 21:23
 */
public class StaticEntityModelProperties extends DataFileProcessor {
    private int texOff;
    private int level;

    private static final String TEX_OFF = "texoff";
    private static final String LEVEL = "lvl";

    public StaticEntityModelProperties(InputStream properties) throws IOException, ParseException {
        super(properties);
    }

    @Override
    protected boolean process(String target, String[] args, int i) throws ParseException {
        switch(target){
            case TEX_OFF:
                checkArgsAmount(args, 1, i);
                this.texOff = Integer.parseInt(args[0]);
                break;
            case LEVEL:
                checkArgsAmount(args, 1, i);
                this.level = Integer.parseInt(args[0]);
                break;
            default:
                return false;
        }
        return true;
    }

    public int getLevel() {
        return level;
    }

    public int getTexOff() {
        return texOff;
    }
}

