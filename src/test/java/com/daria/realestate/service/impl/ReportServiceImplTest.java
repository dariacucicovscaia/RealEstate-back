package com.daria.realestate.service.impl;


public class ReportServiceImplTest {

//    private ReportService serviceUnderTest;
//
//    @Before
//    public void before() {
//        serviceUnderTest = new ReportServiceImpl();
//    }
//
//    @Test
//    public void saveFileTest() {
//        LocalDateTime from = LocalDateTime.parse("2023-01-05T14:45:29.146130800");
//        LocalDateTime to = LocalDateTime.parse("2023-01-25T14:45:29.147128400");
//
//        String fileName = "C:\\Users\\DCUCICOV\\Documents\\reports\\" + "email" + "\\" + "fullAddress" + "\\report_" + LocalDateTime.now().toLocalDate() + ".xlsx";
//        String sheetName = from.toLocalDate() + "_" + to.toLocalDate();
//        Object[][] body = {
//                {"Start", "End", "Email", "FirstName", "LastName", "PhoneNumber", "AppointmentStatus"},
//                {"2023-01-05 14:45:29.146130800", "2023-01-25 14:45:29.147128400", "email", "firstName", "lastName", "phoneNumber", AppointmentStatus.CONFIRMED},
//                {"2023-01-05 14:45:29.146130800", "2023-01-25 14:45:29.147128400", "email", "firstName", "lastName", "phoneNumber", AppointmentStatus.CONFIRMED},
//                {"2023-01-05 14:45:29.146130800", "2023-01-25 14:45:29.147128400", "email", "firstName", "lastName", "phoneNumber", AppointmentStatus.CONFIRMED},
//                {"2023-01-05 14:45:29.146130800", "2023-01-25 14:45:29.147128400", "email", "firstName", "lastName", "phoneNumber", AppointmentStatus.CONFIRMED},
//        };
//
//        String path = serviceUnderTest.generateLocalReport(sheetName, body);
//        Assert.assertEquals(path, fileName);
//
//        Object[][] result = serviceUnderTest.readFile(fileName);
//        int rows = result.length;
//        int cols = result[0].length;
//
//        for (int r = 0; r < rows; r++) {
//            for (int c = 0; c < cols; c++) {
//                Assert.assertEquals(body[r][c].toString(), result[r][c]);
//            }
//        }
//    }


}