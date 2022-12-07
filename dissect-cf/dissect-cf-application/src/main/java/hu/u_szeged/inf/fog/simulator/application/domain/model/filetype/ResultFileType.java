package hu.u_szeged.inf.fog.simulator.application.domain.model.filetype;

public enum ResultFileType implements FileType {
    TIMELINE,
    DEVICES_ENERGY,
    NODES_ENERGY;

    @Override
    public String typeExtension() {
        return ".html";
    }

    @Override
    public String contentType() {
        return "text/html";
    }
}
