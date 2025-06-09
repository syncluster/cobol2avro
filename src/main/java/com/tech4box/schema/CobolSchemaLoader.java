package com.tech4box.schema;

import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.External.CopybookLoader;
import com.tech4box.JRecord.IO.CobolIoProvider;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;

public class CobolSchemaLoader {

    public static LayoutDetail loadLayoutFromCobol(String cobolFilePath, String encoding) throws Exception {
        CobolIoProvider ioProvider = CobolIoProvider.getInstance();

        ICobolIOBuilder ioBuilder = ioProvider.newIOBuilder(cobolFilePath)
                .setFileOrganization(Constants.IO_FIXED_LENGTH)
                .setFont(encoding) // or UTF-8 depending on encoding
                .setSplitCopybook(CopybookLoader.SPLIT_NONE)
                .setDropCopybookNameFromFields(true);

        return ioBuilder.getLayout();
    }
}
