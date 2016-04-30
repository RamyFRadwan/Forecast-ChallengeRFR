//package romeo.com.forecastchallenge;
//
//import de.triplet.simpleprovider.AbstractProvider;
//import de.triplet.simpleprovider.Column;
//import de.triplet.simpleprovider.Table;
//
///**
// * Created by RamyFRadwan on 30/04/2016.
// */
//
// Content provider to save data for offline use.
//
//public class ForecastDB_Provider extends AbstractProvider {
//    @Override
//    protected String getAuthority() {
//        return "romeo.com.forecastchallenge.DATA";
//    }
//    @Table
//    public class DayForecast{
//        @Column(value = Column.FieldType.INTEGER, primaryKey = true)
//        public static final String KEY_ID = "_id";
//
//        @Column(Column.FieldType.TEXT)
//        public static final String KEY_TITLE = "title";
//
//        @Column(Column.FieldType.TEXT)
//        public static final String KEY_TEXT = "text";
//
//        @Column(Column.FieldType.INTEGER)
//        public static final String KEY_HI = "hi";
//
//        @Column(Column.FieldType.INTEGER)
//        public static final String KEY_LOW = "low";
//
//        @Column(Column.FieldType.TEXT)
//        public static final String KEY_CONDITION = "condition";
//
//        @Column(Column.FieldType.TEXT)
//        public static final String KEY_DATE = "date";
//
//        @Column(Column.FieldType.TEXT)
//        public static final String KEY_ICON_URL = "iconUrl";
//
//
//    }
//}
