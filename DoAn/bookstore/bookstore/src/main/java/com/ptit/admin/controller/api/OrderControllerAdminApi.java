package com.ptit.admin.controller.api;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ptit.model.Book;
import com.ptit.model.Order;
import com.ptit.model.OrderDetail;
import com.ptit.repository.OrderDetailDao;
import com.ptit.service.OrderDetailService;
import com.ptit.service.OrderService;
@RestController
@RequestMapping("api/admin")
public class OrderControllerAdminApi {
	@Autowired
	private OrderService orderService;
	
	
	@Autowired
	private OrderDetailService orderDetailService;
//	@RequestMapping(value = "order/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Order> find(@PathVariable("id") long id,HttpServletResponse respon){
//		try {			
//			return new ResponseEntity<Order>(orderService.getOrderById(id),HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
	
	@RequestMapping(value = "order/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public void find(@PathVariable("id") long id,HttpServletResponse respon){
		try {
			Order order=orderService.getOrderById(id);
			List<OrderDetail> list = orderDetailService.getListDetailByOrderId(id);
			List<Book> listBook = orderDetailService.getListBookOfOrderDetail(id);
			
			respon.setContentType("text/html;charset=UTF-8");
			PrintWriter out = respon.getWriter();
			
			out.println("<header class=\"card-header\"> Chi tiết đơn hàng</header>\r\n"
					+ "							<div class=\"card-body\">\r\n"
					+ "								<h6>\r\n"
					+ "									Mã đơn hàng: <span id=\"order-id\">"+order.getOrderId()+"</span>\r\n"
					+ "								</h6>\r\n"
					+ "								<article class=\"card\">\r\n"
					+ "									<div class=\"card-body row\">\r\n"
					+ "										<div class=\"col\">\r\n"
					+ "											<strong>Khách hàng:</strong> <br> <span\r\n"
					+ "												id=\"order-status\">"+order.getNameOfCustomer()+" | "+order.getPhoneOfCustomer()+"</span>\r\n"
					+ "										</div>\r\n"
					+ "										<div class=\"col\">\r\n"
					+ "											<strong>Thời gian:</strong> <br> <span\r\n"
					+ "												id=\"order-day\">"+order.getOrderDay()+"</span>\r\n"
					+ "										</div>\r\n"
					+ "										<div class=\"col\">\r\n"
					+ "											<strong>Đơn vị giao:</strong> <br> SQT Express | <i\r\n"
					+ "												class=\"fa fa-phone\"></i> <span> +171.162.236</span>\r\n"
					+ "										</div>\r\n"
					+ "									</div>\r\n"
					+ "								</article>\r\n"
					+ "								<div class=\"track\">\r\n"
					+ "									<div class=\"ready step active\">\r\n"
					+ "									<span class=\"icon \"> <i class=\"fa fa-box\"></i>\r\n"
					+ "									</span> <span class=\"text\">Chờ xác nhận</span>\r\n"
					+ "								</div>\r\n"
					+ "								<div class=\"confirm step "+((order.getOrderStatus()==2 || order.getOrderStatus()==4)?"active":"")+"\">\r\n"
					+ "									<span class=\"icon\"> <i class=\"fa fa-check\"></i>\r\n"
					+ "									</span> <span class=\"text\">Xác nhận</span>\r\n"
					+ "								</div>\r\n"
					+ "								<div class=\"done step "+(order.getOrderStatus()==2||order.getOrderStatus()==4?"active":"")+"\">\r\n"
					+ "									<span class=\"icon\"> <i class=\""+((order.getOrderStatus()==4 || order.getOrderStatus()==3)?"fas fa-times":"fa fa-user")+"\"></i>\r\n"
					+ "									</span> <span class=\"text\"> "+((order.getOrderStatus()==4 || order.getOrderStatus()==3 )?"Đã hủy":"Đã giao")+" </span>\r\n"
					+ "								</div>\r\n"
					+ "								</div>\r\n"
					+ "								<hr>\r\n"
					+ "								<ul class=\"row order-detail\" id=\"listOrderDetail\">");
			
			for (int i = 0; i < list.size(); i++) {
				out.println("<li class=\"col-md-4\">\r\n"
						+ "									<figure class=\"itemside mb-3\">\r\n"
						+ "										<div class=\"aside\">\r\n"
						+ "											<img src=\"/image/"+listBook.get(i).getPicture()+"\"\r\n"
						+ "												class=\"img-sm border\">\r\n"
						+ "										</div>\r\n"
						+ "										<figcaption class=\"info align-self-center\">\r\n"
						+ "											<p class=\"title\" id=\"book-name\">"+listBook.get(i).getBookName()+" <br> \r\n"
						+ "												<span class=\"text-muted\" id=\"book-author\"> "+listBook.get(i).getAuthor().getName()+"</span>\r\n"
						+ "											</p>\r\n"
						+ "											<span class=\"text-muted\" id=\"book-price\">"+NumberFormat.getNumberInstance(java.util.Locale.UK).format(list.get(i).getPrice())+"VNĐ</span>\r\n"
						+ "											x <span class=\"text-muted\" id=\"order-quantity\">"+list.get(i).getQuantity()+"</span>\r\n"
						+ "										</figcaption>\r\n"
						+ "									</figure>\r\n"
						+ "								</li>	");
			}
			
			out.println("</ul>\r\n"
					+ "\r\n"
					+ "								<hr>\r\n"
					+ "								<p class=\"text text-right\">Thành tiền: "
					+ "									<strong class=\"text-danger text-boild\">"+NumberFormat.getNumberInstance(java.util.Locale.UK).format(order.getTotalPrice())+" VNĐ </strong>\r\n "
					+ "								</p>\r\n"
					+ "								<hr>\r\n"
					+ "								<a style=\"color: white;cursor: pointer;\" class=\"btn btn-warning btn-close-from\" onclick=\"closeOrderDetail()\"\r\n"
					+ "									data-abc=\"true\"> <i class=\"fa fa-chevron-left\"></i> Trở về\r\n"
					+ "								</a>\r\n"
					+ "							</div>");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
