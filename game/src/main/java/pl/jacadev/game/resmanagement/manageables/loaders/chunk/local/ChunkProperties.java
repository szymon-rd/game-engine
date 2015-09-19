package pl.jacadev.game.resmanagement.manageables.loaders.chunk.local;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.game.resmanagement.ResourceManager;
import pl.jacadev.game.resmanagement.manageables.loaders.DataFileProcessor;
import pl.jacadev.game.resmanagement.manageables.loaders.staticentitymodel.local.StaticEntityModelDesc;
import pl.jacadev.game.world.entities.staticentities.StaticEntity;
import pl.jacadev.game.world.entities.staticentities.worldcomponents.StaticEntityModel;
import pl.jacadev.game.world.terrain.TerrainResources;
import pl.jacadev.game.resmanagement.manageables.ManageableStaticEntity;
import pl.jacadev.engine.graphics.shaders.programs.light.SpotLightSource;
import pl.jacadev.engine.graphics.shaders.programs.light.Lighting;
import pl.jacadev.engine.graphics.shaders.programs.terrain.MaterialSet;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * @author Jaca777
 *         Created 02.01.15 at 21:45
 */
public class ChunkProperties extends DataFileProcessor {

    public int x, y;
    public float maxHeight;
    public float minHeight;
    public Lighting lighting;
    public MaterialSet materialSet;
    public StaticEntity[] staticEntities;
    public Vector2f[] waterCoords;

    private static final String SEPARATOR = ": ";

    private static final String POSITION = "pos";
    private static final String MAX_HEIGHT = "maxh";
    private static final String MIN_HEIGHT = "minh";
    private static final String LIGHTS_AMOUNT = "la";
    private static final String LIGHT_SRC = "lsrc";
    private static final int LIGHT_ARGS_AMOUNT = 11;
    private static final String MATERIALS_SET = "mat";
    private static final String MATERIAL_STANDARD = "s";
    private static final String MATERIAL_CUSTOM = "c";
    private static final String ENTITIES_AMOUNT = "ea";
    private static final String ENTITY_XZ = "e";
    private static final String ENTITY_XYZ = "ey";
    private static final String WTR_SRF_AMOUNT = "wsa";
    private static final String WTR_SRF = "ws";

    public ChunkProperties(InputStream properties) throws IOException, ParseException {
        super(properties);
    }

    private int entityPointer = 0,
                wtrPointer = 0;

    @Override
    protected boolean process(String target, String[] args, int i) throws ParseException {
        switch (target) {
            case POSITION:
                checkArgsAmount(args, 2, i);
                this.x = Integer.parseInt(args[0]);
                this.y = Integer.parseInt(args[1]);
                break;
            case MAX_HEIGHT:
                checkArgsAmount(args, 1, i);
                this.maxHeight = Float.parseFloat(args[0]);
                break;
            case MIN_HEIGHT:
                checkArgsAmount(args, 1, i);
                this.minHeight = Float.parseFloat(args[0]);
                break;
            case LIGHTS_AMOUNT:
                checkArgsAmount(args, 1, i);
                this.lighting = new Lighting();
                break;
            case LIGHT_SRC:
                checkArgsAmount(args, LIGHT_ARGS_AMOUNT, i);
                if (this.lighting == null)
                    throw new ParseException("Unable to load lighting - Array size is unknown.", i);
                this.lighting.addLightSource(new SpotLightSource(
                        new Vector3f(Float.parseFloat(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2])),
                        new Vector3f(Float.parseFloat(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5])),
                        new Vector3f(Float.parseFloat(args[6]), Float.parseFloat(args[7]), Float.parseFloat(args[8])),
                        Float.parseFloat(args[9]), Float.parseFloat(args[10]), false));
                break;
            case ENTITIES_AMOUNT:
                checkArgsAmount(args, 1, i);
                this.staticEntities = new StaticEntity[Integer.parseInt(args[0])];
                break;
            case ENTITY_XZ:
                checkArgsAmount(args, 3, i);
                StaticEntityModel model = (StaticEntityModel) ResourceManager.use(new StaticEntityModelDesc(args[0]));
                float x = Float.parseFloat(args[1]);
                float z = Float.parseFloat(args[2]);
                this.staticEntities[entityPointer] = new ManageableStaticEntity(model, new Vector3f(x, model.getLvl(), z), true);
                entityPointer++;
                break;
            case ENTITY_XYZ:
                checkArgsAmount(args, 4, i);
                StaticEntityModel entityModel = (StaticEntityModel) ResourceManager.use(new StaticEntityModelDesc(args[0]));
                this.staticEntities[entityPointer] = new ManageableStaticEntity(entityModel, new Vector3f(Float.parseFloat(args[1]),Float.parseFloat(args[2]),Float.parseFloat(args[3])), false);
                entityPointer++;
                break;
            case MATERIALS_SET:
                checkArgsAmount(args, 1, i);
                this.materialSet = TerrainResources.getMaterialSet(Integer.parseInt(args[0]));
                break;
            case WTR_SRF_AMOUNT:
                checkArgsAmount(args, 1, i);
                this.waterCoords = new Vector2f[4 * Integer.parseInt(args[0])];
                break;
            case WTR_SRF:
                checkArgsAmount(args, 8, i);
                this.waterCoords[wtrPointer * 4] = new Vector2f(Float.parseFloat(args[0]), Float.parseFloat(args[1]));
                this.waterCoords[wtrPointer * 4 + 1] = new Vector2f(Float.parseFloat(args[2]), Float.parseFloat(args[3]));
                this.waterCoords[wtrPointer * 4 + 2] = new Vector2f(Float.parseFloat(args[4]), Float.parseFloat(args[5]));
                this.waterCoords[wtrPointer * 4 + 3] = new Vector2f(Float.parseFloat(args[6]), Float.parseFloat(args[7]));
                wtrPointer++;
                break;
            default:
                return false;
        }
        return true;
    }

}
