package fm.FmDialog;

/**
 * Created by Administrator on 10/25/2016.
 */

public class DialogState {

    public class Register {
        public static final int INFOMATION_NOT_COMPLETE = 7;

        public static final int FORMAT_ERROR_USERNAME = 1;
        public static final int FORMAT_ERROR_PASSWORD = 2;
        public static final int PASSWORD_NOT_MACTH = 3;

        public static final int FACE_IDENTITY_ERROR = 4;

        public static final int ALREDY_REGISTER = 5;
        public static final int REGISTER_SUCCESS = 6;
    }

    public class Detect {
        public static final int FACE_ERROR_1 = 301;
        public static final int FACE_ERROR_2 = 302;
        public static final int FACE_ERROR_3 = 303;

        public static final int FACE_DETECT_SUCCESS = 304;

        public static final int FACE_EXSIT = 305;

        public static final int FACE_CAN_REGISTER = 306;
        public static final int FACE_NOT_REGISTER = 307;
    }
}
