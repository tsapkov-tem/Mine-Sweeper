package mineSweeper;

import mineSweeper.Bot.ScriptForWin;

public class SingletonGetClass {
    private static FieldCreate fieldCreate;
    private static PaintField paintField;
    private static MainClass mainClass;
    private static CreateNewLevel createNewLevel;
    private static ScriptForWin scriptForWin;

    public ScriptForWin getScriptForWin() {
        if (scriptForWin == null) {
            scriptForWin = new ScriptForWin();
        }
        return scriptForWin;
    }

    public CreateNewLevel getCreateNewLevel() {
        if (createNewLevel == null) {
            createNewLevel = new CreateNewLevel();
        }
        return createNewLevel;
    }

    public FieldCreate getFieldCreate() {
        if (fieldCreate == null){
            fieldCreate = new FieldCreate ();
        }
        return fieldCreate;
    }

    public PaintField getPaintField() {
        if (paintField==null){
            paintField = new PaintField (this);
        }
        return paintField;
    }

    public MainClass getMainClass() {
        if (mainClass == null){
            mainClass = new MainClass (this);
        }
        return mainClass;
    }
}
