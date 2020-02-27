//package com.meikinfo.erukaprovide.erukaprovide.util;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//
///**
// * 描述:
// * ExcelFieldReflectionUtil
// *
// * @author hongjw
// * @create 2020-02-27 9:49
// */
//public class ExcelFieldReflectionUtil {
//    /**
//     * 将字段转为相应的格式
//     * @param cell
//     * @return
//     */
//    private static Object getCellFormatValue(Row row, Cell cell) {
//        Object cellValue = null;
//        if (cell != null) {
//            //判断cell类型
//            switch (cell.getCellType()) {
//                case Cell.CELL_TYPE_NUMERIC: {
//
//                    break;
//                }
//                case Cell.CELL_TYPE_FORMULA: {
//                    if (DateUtil.isCellDateFormatted(cell)) {
//                        cellValue = cell.getDateCellValue();////转换为日期格式YYYY-mm-dd
//                    } else {
//                        cellValue = String.valueOf(cell.getNumericCellValue()); //数字
//                    }
//                    break;
//                }
//                case Cell.CELL_TYPE_STRING: {
//                    cellValue = cell.getRichStringCellValue().getString();
//                    break;
//                }
//                default:
//                    cellValue = "";
//            }
//        } else {
//            cellValue = "";
//        }
//        return cellValue;
//    }
//}
