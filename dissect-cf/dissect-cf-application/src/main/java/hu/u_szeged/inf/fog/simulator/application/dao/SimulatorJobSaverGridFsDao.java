package hu.u_szeged.inf.fog.simulator.application.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.FileType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * A Grid FS Data Access Object for saving job related files.
 *
 * @author Balazs Lehoczki
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SimulatorJobSaverGridFsDao {

    @NonNull
    private final GridFsTemplate gridFsTemplate;

    public <T extends FileType> Map<String, ObjectId> saveFiles(@NonNull MultiValueMap<T, File> inputs) {
        final Map<String, ObjectId> result = new LinkedHashMap<>();

        for (MultiValueMap.Entry<T, List<File>> entry : inputs.entrySet()) {
            var type = entry.getKey().name();
            var contentType = entry.getKey().contentType();

            if (entry.getValue().size() == 1) {
                result.put(type, saveFile(contentType, entry.getValue().get(0)));
            } else {
                IntStream.range(0, entry.getValue().size()).forEach(i -> {
                    result.put(type + i, saveFile(contentType, entry.getValue().get(i)));
                });
            }
        }

        return result;
    }

    protected ObjectId saveFile(String contentType, File file) {
        try (var inputStream = new FileInputStream(file)) {
            return gridFsTemplate.store(inputStream, file.getName(), contentType);
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't save file into the database!");
        }
    }
}
