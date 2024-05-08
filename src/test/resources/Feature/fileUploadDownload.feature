Feature: verify file upload and download

  @FileUpload
  Scenario: verify file upload
    Given I setup request structure to upload the file
    When I hit an api to upload the file
    Then I verify file is getting uploaded successfully

  Scenario: verify file download using inputStream
    Given I setup request structure to download the file
    When I hit an api to download the file
    Then I verify file is getting downloaded successfully

  @FileDownload
  Scenario: verify file download using byte array
    Given I setup request structure to download the file
    When I hit an api to download the file
    Then I verify file is getting downloaded successfully using byteStream