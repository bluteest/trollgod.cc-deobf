//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import me.hollow.realth.client.modules.*;
import java.util.stream.*;
import java.nio.file.attribute.*;
import java.nio.file.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.*;
import com.google.gson.*;
import java.io.*;
import java.util.*;

public class ConfigManager
{
    public final ArrayList<Module> features;
    public String config;
    
    public ConfigManager() {
        this.features = new ArrayList<Module>();
        this.config = "TrollGod/config/";
    }
    
    public void loadConfig(final String name) {
        final List<File> files = Arrays.stream((Object[])Objects.requireNonNull((T[])new File("TrollGod").listFiles())).filter(File::isDirectory).collect((Collector<? super Object, ?, List<File>>)Collectors.toList());
        if (files.contains(new File("TrollGod/" + name + "/"))) {
            this.config = "TrollGod/" + name + "/";
        }
        else {
            this.config = "TrollGod/config/";
        }
        for (final Module module : this.features) {
            try {
                this.loadSettings(module);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.saveCurrentConfig();
    }
    
    public void saveConfig(final String name) {
        this.config = "TrollGod/" + name + "/";
        final File path = new File(this.config);
        if (!path.exists()) {
            path.mkdir();
        }
        for (final Module feature : this.features) {
            try {
                this.saveSettings(feature);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.saveCurrentConfig();
    }
    
    public void saveCurrentConfig() {
        final File currentConfig = new File("TrollGod/currentconfig.txt");
        try {
            if (currentConfig.exists()) {
                final FileWriter writer = new FileWriter(currentConfig);
                final String tempConfig = this.config.replaceAll("/", "");
                writer.write(tempConfig.replaceAll("TrollGod", ""));
                writer.close();
            }
            else {
                currentConfig.createNewFile();
                final FileWriter writer = new FileWriter(currentConfig);
                final String tempConfig = this.config.replaceAll("/", "");
                writer.write(tempConfig.replaceAll("TrollGod", ""));
                writer.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String loadCurrentConfig() {
        final File currentConfig = new File("TrollGod/currentconfig.txt");
        String name = "config";
        try {
            if (currentConfig.exists()) {
                final Scanner reader = new Scanner(currentConfig);
                while (reader.hasNextLine()) {
                    name = reader.nextLine();
                }
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
    
    public void saveSettings(final Module feature) throws IOException {
        final JsonObject object = new JsonObject();
        final File directory = new File(this.config + this.getDirectory(feature));
        if (!directory.exists()) {
            directory.mkdir();
        }
        final String featureName;
        final Path outputFile;
        if (!Files.exists(outputFile = Paths.get(featureName = this.config + this.getDirectory(feature) + feature.getLabel() + ".json", new String[0]), new LinkOption[0])) {
            Files.createFile(outputFile, (FileAttribute<?>[])new FileAttribute[0]);
        }
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String json = gson.toJson((JsonElement)this.writeSettings(feature));
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(outputFile, new OpenOption[0])));
        writer.write(json);
        writer.close();
    }
    
    public static void setValueFromJson(final Module feature, final Setting setting, final JsonElement element) {
        if (setting.getName().equals("Enabled")) {
            if (element.getAsBoolean()) {
                feature.setEnabled(true);
            }
            return;
        }
        final String type = setting.getType();
        switch (type) {
            case "Boolean": {
                setting.setValue((Object)element.getAsBoolean());
                break;
            }
            case "Double": {
                setting.setValue((Object)element.getAsDouble());
                break;
            }
            case "Float": {
                setting.setValue((Object)element.getAsFloat());
                break;
            }
            case "Integer": {
                setting.setValue((Object)element.getAsInt());
                break;
            }
            case "String": {
                final String str = element.getAsString();
                setting.setValue((Object)str.replace("_", " "));
                break;
            }
            case "Bind": {
                setting.setValue((Object)new Bind.BindConverter().doBackward(element));
                break;
            }
            case "Enum": {
                try {
                    final EnumConverter converter = new EnumConverter((Class)((Enum)setting.getValue()).getClass());
                    final Enum value = converter.doBackward(element);
                    setting.setValue((value == null) ? setting.getDefaultValue() : value);
                }
                catch (Exception ex) {}
                break;
            }
        }
    }
    
    public void init() {
        this.features.addAll(JordoHack.INSTANCE.getModuleManager().getModules());
        final String name = this.loadCurrentConfig();
        this.loadConfig(name);
    }
    
    private void loadSettings(final Module feature) throws IOException {
        final String featureName = this.config + this.getDirectory(feature) + feature.getLabel() + ".json";
        final Path featurePath = Paths.get(featureName, new String[0]);
        if (!Files.exists(featurePath, new LinkOption[0])) {
            return;
        }
        this.loadPath(featurePath, feature);
    }
    
    private void loadPath(final Path path, final Module feature) throws IOException {
        final InputStream stream = Files.newInputStream(path, new OpenOption[0]);
        try {
            loadFile(new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject(), feature);
        }
        catch (IllegalStateException e) {
            loadFile(new JsonObject(), feature);
        }
        stream.close();
    }
    
    private static void loadFile(final JsonObject input, final Module feature) {
        for (final Map.Entry entry : input.entrySet()) {
            final String settingName = entry.getKey();
            final JsonElement element = entry.getValue();
            boolean settingFound = false;
            for (final Setting setting : feature.getSettings()) {
                if (!settingName.equals(setting.getName())) {
                    continue;
                }
                try {
                    setValueFromJson(feature, setting, element);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                settingFound = true;
            }
            if (!settingFound) {
                continue;
            }
        }
    }
    
    public JsonObject writeSettings(final Module feature) {
        final JsonObject object = new JsonObject();
        final JsonParser jp = new JsonParser();
        for (final Setting setting : feature.getSettings()) {
            if (setting.isEnumSetting()) {
                final EnumConverter converter = new EnumConverter((Class)((Enum)setting.getValue()).getClass());
                object.add(setting.getName(), converter.doForward((Enum)setting.getValue()));
            }
            else {
                if (setting.isStringSetting()) {
                    final String str = (String)setting.getValue();
                    setting.setValue((Object)str.replace(" ", "_"));
                }
                try {
                    object.add(setting.getName(), jp.parse(setting.getValueAsString()));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }
    
    public String getDirectory(final Module feature) {
        String directory = "";
        if (feature instanceof Module) {
            directory = directory + feature.getCategory().getLabel() + "/";
        }
        return directory;
    }
}
