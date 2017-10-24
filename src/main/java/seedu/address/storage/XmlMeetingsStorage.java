package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UniqueMeetingList;

/**
 * A class to access Meeting data stored as an xml file on the hard disk.
 */
public class XmlMeetingsStorage implements MeetingsStorage {

    // Creates a new folder for all backup data
    //private static final String BACKUP_FILE_PREFIX = "backup/";
    private static final Logger logger = LogsCenter.getLogger(XmlMeetingsStorage.class);

    private String filePath;
    //private String backupFilePath;

    public XmlMeetingsStorage(String filePath) {
        this.filePath = filePath;
        //this.backupFilePath = BACKUP_FILE_PREFIX + filePath;
    }

    public String getMeetingsFilePath() {
        return filePath;
    }

    /*
    public String getBackupFilePath() {
        return backupFilePath;
    }
    */

    @Override
    public Optional<UniqueMeetingList> readMeetings() throws DataConversionException, IOException {
        return readMeetingList(filePath);
    }

    /*
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<UniqueMeetingList> readMeetingList(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File meetingListFile = new File(filePath);

        if (!meetingListFile.exists()) {
            logger.info("MeetingList file "  + meetingListFile + " not found");
            return Optional.empty();
        }

        UniqueMeetingList meetingListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath),
                UniqueMeetingList.class);

        return Optional.of(meetingListOptional);
    }

    @Override
    public void saveMeetingList(UniqueMeetingList meetingList) throws IOException {
        saveMeetingList(meetingList, filePath);
    }

    /**
     * Similar to {@link #saveMeetingList(UniqueMeetingList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveMeetingList(UniqueMeetingList meetingList, String filePath) throws IOException {
        requireNonNull(meetingList);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableMeetingList(meetingList));
    }

    /*
    @Override
    public void backupMeetingList(UniqueMeetingList meetingList) throws IOException {
        saveMeetingList(meetingList, backupFilePath);
    }

    public Optional<UniqueMeetingList> restoreMeetingList() throws IOException, DataConversionException {
        return readMeetingList(backupFilePath);
    }
    */


}
