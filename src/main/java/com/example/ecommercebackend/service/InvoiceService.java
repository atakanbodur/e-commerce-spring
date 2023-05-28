package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.Customer;
import com.example.ecommercebackend.model.Order;
import com.example.ecommercebackend.model.OrderItem;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.repository.CustomerRepository;
import com.example.ecommercebackend.repository.OrderRepository;
import com.example.ecommercebackend.repository.OrderItemRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public byte[] generateInvoicePdf(Order order) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Add header
            document.add(new Paragraph("Invoice")
                    .setBold()
                    .setFontSize(24)
                    .setTextAlignment(TextAlignment.CENTER));

            // Add order details
            document.add(new Paragraph("Order ID: " + order.getOrderid()));
            document.add(new Paragraph("Order Date: " + order.getOrderdate()));
            document.add(new Paragraph("Order Status: " + order.getStatus()));

            // Add customer details
            Optional<Customer> customerOptional = customerRepository.findById(order.getCustomerid());
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                document.add(new Paragraph("Customer Name: " + customer.getName()));
                document.add(new Paragraph("Customer Email: " + customer.getEmail()));
                document.add(new Paragraph("Customer Address: " + customer.getAddress()));
                document.add(new Paragraph("Customer Phone: " + customer.getPhone()));
            }
            // Add a table for the products
            float[] columnWidths = {2, 2, 2, 2, 1};
            Table table = new Table(columnWidths);
            table.setWidth(500).setFixedLayout();
            table.addHeaderCell("Product");
            table.addHeaderCell("Qty");
            table.addHeaderCell("Price");
            table.addHeaderCell("Total");
            List<OrderItem> listOfOrders = orderItemRepository.findByOrderid(order.getOrderid());

            for (OrderItem orderItem : listOfOrders) {
                Product product = productRepository.findById(orderItem.getProductid()).get();
                table.addCell(product.getName());
                table.addCell(String.valueOf(orderItem.getQuantity()));
                table.addCell(String.valueOf(orderItem.getPrice()));
                table.addCell(String.valueOf(orderItem.getPrice() * orderItem.getQuantity()));
            }

            document.add(table);

            // Add total price
            document.add(new Paragraph("Total Price: " + order.getTotalprice())
                    .setBold()
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.RIGHT));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public ResponseEntity<ByteArrayResource> getInvoice(Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            byte[] pdfContent = generateInvoicePdf(order);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice_" + orderId + ".pdf");
            headers.setContentLength(pdfContent.length);

            ByteArrayResource resource = new ByteArrayResource(pdfContent);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
