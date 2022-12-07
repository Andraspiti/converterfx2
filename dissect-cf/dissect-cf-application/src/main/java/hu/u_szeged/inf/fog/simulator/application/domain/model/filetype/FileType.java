package hu.u_szeged.inf.fog.simulator.application.domain.model.filetype;

public interface FileType {

    String name();

    String typeExtension();

    String contentType();

    default String fileName() {
        return this.name() + this.typeExtension();
    }
}
