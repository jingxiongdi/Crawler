package com.example.crawler.utils;

import com.example.crawler.bean.ZhiLianBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by jingxiongdi on 2018/4/18.
 */

public class ExcelUtils {
    /**
     * 对象数据写入到Excel
     */
    public static void writeExcel(String path, String excelFileName, ArrayList<ZhiLianBean> stuList ) {
        WritableWorkbook book = null;
        try {
            // 打开文件
            book = Workbook.createWorkbook(new File(path));
            // 生成名为"学生"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet(excelFileName, 0);

            if(stuList!=null && !stuList.isEmpty()){
                for(int i=0; i<stuList.size(); i++){
                    sheet.addCell(new Label(0, i, stuList.get(i).getCompanyName()));
                    sheet.addCell(new Label(1, i, stuList.get(i).getJobs()));
                    sheet.addCell(new Label(2, i, stuList.get(i).getSalary()));
                    sheet.addCell(new Label(3, i, stuList.get(i).getResponsibilities()));
                }
            }

            // 写入数据并关闭文件
            book.write();
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(book!=null){
                try {
                    book.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
