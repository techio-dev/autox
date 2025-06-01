// Test script để kiểm tra module LTT
// File này có thể được sử dụng để test chức năng của module

console.log("=== LTT Module Test Script ===");
console.log("Script đang chạy từ module LTT");
console.log(`Thời gian: ${new Date().toLocaleString()}`);

// Test basic functions
try {
	console.log(`Device info: ${device.brand} ${device.model}`);
	console.log(`Android version: ${device.release}`);
} catch (e) {
	console.log(`Không thể lấy thông tin device: ${e}`);
}

// Test toast
try {
	toast("LTT Module đang hoạt động!");
} catch (e) {
	console.log(`Không thể hiển thị toast: ${e}`);
}

console.log("=== Test hoàn thành ===");
