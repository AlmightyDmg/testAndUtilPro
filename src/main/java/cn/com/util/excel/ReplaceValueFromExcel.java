package cn.com.util.excel;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @description 批量替换excel中的值
 * @author zhum
 * @date 2019年8月14日 17:12:40
 */
public class ReplaceValueFromExcel {

    public static void main(String[] args) {
        Map item = new HashMap();
        item.put("L-00001","L-00012");
        item.put("L-00002","L-00013");
        item.put("L-00003","L-00014");

        String path =  "C:\\Users\\xxx\\Desktop\\111.xls";
        String path2 = "C:\\Users\\xxx\\Desktop\\112.xls";
        replaceModel(item, path, path2);
    }

    /**
     *
     * @Description:批量替换excel中的值 目前仅支持 xls格式的Excel
     * @author zhumeng
     *
     * @param items ("L-00001","L-00012")  key代表原来的值 value代表新的值
     * @param sourceFilePath
     * @param targetFilePath
     *
     * @date Jun 14, 2019 5:22:30 PM
     */
    public static boolean replaceModel(Map items, String sourceFilePath, String targetFilePath) {
        boolean bool = true;
        try {
            POIFSFileSystem fs  =new POIFSFileSystem(new FileInputStream(sourceFilePath));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            while(rows.hasNext()){
                HSSFRow row = (HSSFRow) rows.next();
                if(row!=null) {
                    int num = row.getLastCellNum();
                    for(int i=0;i<num;i++) {
                        HSSFCell cell=  row.getCell(i);
                        if(cell!=null) {
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        }
                        if(cell==null || cell.getStringCellValue()==null) {
                            continue;
                        }
                        String value= cell.getStringCellValue();
                        if(!"".equals(value)) {
                            Set<String> keySet = items.keySet();
                            Iterator<String> it = keySet.iterator();
                            while (it.hasNext()) {
                                String text = it.next();
                                if(value.equalsIgnoreCase(text)) {
                                    cell.setCellValue((String)items.get(text));
                                    break;
                                }
                            }
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }
            }
            FileOutputStream fileOut = new FileOutputStream(targetFilePath);
            wb.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            bool = false;
            e.printStackTrace();
        }
        return bool;
    }
}
