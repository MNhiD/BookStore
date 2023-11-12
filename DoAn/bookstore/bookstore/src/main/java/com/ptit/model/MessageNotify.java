package com.ptit.model;

public class MessageNotify {
    public static String MESSAGE_TEST = "Test 1";


    //AuthorController
    public static String AUTHOR_SAVE_SUCCESS = "Thêm tác giả thành công";
    public static String AUTHOR_EDIT_SUCCESS = "Sửa tác giả thành công";
    public static String AUTHOR_DEL_SUCCESS = "Xóa tác giả thành công";
    public static String AUTHOR_DEL_FAILED = "Xóa thất bại, Đã có sách thuộc tác giả này trong hệ thống.";
    public static String AUTHOR_SAVE_FAILED_001 = "Thêm thất bại. Tác giả \"";
    public static String AUTHOR_SAVE_FAILED_002 = "\" đã có trong hệ thống.";
    public static String AUTHOR_EDIT_FAILED_001 = "Sửa thất bại. Tác giả \"";
    public static String AUTHOR_EDIT_FAILED_002 = "\" đã có trong hệ thống.";


    //BookController
    public static String BOOK_SAVE_SUCCESS = "Thêm sách thành công";
    public static String BOOK_EDIT_SUCCESS = "Cập nhật sách thành công";
    public static String BOOK_DEL_FAILED_001 = "Xóa thất bại. Sách đã có đơn hàng trong hệ thống. (Gợi ý: Cập nhập lại số lượng sách về 0)";
    public static String BOOK_DEL_FAILED_002 = "Xóa thất bại. Sách đã có lượt bình luận trong hệ thống. (Gợi ý: Xóa các lượt bình luận)";
    public static String BOOK_DEL_FAILED_003 = "Xóa thất bại. Sách đã được thêm vào giỏ hàng trong hệ thống. (Gợi ý: Kiểm tra lại các giỏ hàng)";
    public static String BOOK_DEL_SUCCESS = "Xóa sách thành công";


    //CategoryController
    public static String CATEGORY_SAVE_SUCCESS = "Thêm thể loại thành công";
    public static String CATEGORY_EDIT_SUCCESS = "Sửa thể loại thành công";
    public static String CATEGORY_DEL_SUCCESS = "Xóa thể loại thành công";
    public static String CATEGORY_DEL_FAILED = "Xóa thất bại. Đã có sách thuộc thể loại này trong hệ thống.";
    public static String CATEGORY_SAVE_FAILED_001 = "Thêm thất bại, Thể loại \"";
    public static String CATEGORY_SAVE_FAILED_002 = "\" đã có trong hệ thống.";
    public static String CATEGORY_EDIT_FAILED_001 = "Sửa thất bại, Thể loại \"";
    public static String CATEGORY_EDIT_FAILED_002 = "\" đã có trong hệ thống.";


    //OrderController
    public static String ORDER_SUCCESS = "Cập nhật trạng thái thành công";
    public static String ORDER_FAILED = "Cập nhật trạng thái thất bại";


    //PublishingCompanyController
    public static String PUBLISHINGCOMPANY_SAVE_SUCCESS = "Thêm nhà xuất bản thành công";
    public static String PUBLISHINGCOMPANY_EDIT_SUCCESS = "Sửa nhà xuất bản thành công";
    public static String PUBLISHINGCOMPANY_DEL_SUCCESS = "Xoá nhà xuất bản thành công";
    public static String PUBLISHINGCOMPANY_DEL_FAILED = "Xóa thất bại. Đã có sách thuộc Nhà xuất bản này trong hệ thống.";
    public static String PUBLISHINGCOMPANY_SAVE_FAILED_001 = "Thêm thất bại. Nhà xuất bản \"";
    public static String PUBLISHINGCOMPANY_SAVE_FAILED_002 = "\" đã có trong hệ thống.";
    public static String PUBLISHINGCOMPANY_EDIT_FAILED_001 = "Sửa thất bại. Nhà xuất bản \"";
    public static String PUBLISHINGCOMPANY_EDIT_FAILED_002 = "\" đã có trong hệ thống.";


    //RecoverAccountController
    public static String RECOVERACCOUNT_VERIFY = "Mã xác minh của bạn là: ";


    //ReviewController
    public static String REVIEW_DEL_SUCCESS = "Xóa bình luận thành công";


    //UserAddressController
    public static String USERADDRESS_SAVE_SUCCESS = "Thêm địa chỉ thành công";
    public static String USERADDRESS_EDIT_SUCCESS = "Cập nhật địa chỉ thành công";
    public static String USERADDRESS_DEL_SUCCESS = "Xóa địa chỉ thành công";


    //UserController
    public static String USER_SAVE_SUCCESS = "Thêm user thành công";
    public static String USER_SAVE_FAILED = "Thêm user thất bại, vui lòng kiểm tra lại";
    public static String USER_EDIT_SUCCESS = "Cập nhập user thành công";
    public static String USER_EDIT_FAILED = "Cập nhập user thất bại, vui lòng kiểm tra lại thông tin";
    public static String USER_DEL_SUCCESS = "Xóa user thành công";
    public static String USER_DEL_FAILED = "Xóa user thất bại, người dùng đã có đơn hàng trong hệ thống";


    //AccountController
    public static String SIGNUP_NAME_ERROR = "Tên đăng nhập không được để trống!";
    public static String VALIDATION_NAME_E001 = "Tên đăng nhập đã tồn tại";
    public static String VALIDATION_NAME_E002 = "Hãy chọn tên đăng nhập khác!";
    public static String VALIDATION_PHONE_E001 = "Số điện thoại đã được đăng ký";
    public static String VALIDATION_PHONE_E002 = "Số điện thoại phải đủ 10 số!";
    public  static  String VALIDATION_EMAIL = "Email không hợp lệ!";
    public static String VALIDATION_PASSWORD_E001 = "Mật khẩu phải có tối thiểu ";
    public static String VALIDATION_PASSWORD_E002 = " ký tự!";
    public static String SIGNUP_CONFIRMPASSWORD = "Mật khẩu xác thực không trùng khớp";
}
