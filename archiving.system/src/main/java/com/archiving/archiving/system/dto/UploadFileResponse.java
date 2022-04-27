package com.archiving.archiving.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResponse {

	private String name;
    private String fileDownloadUri;
    private String type;
    private long size;
}
