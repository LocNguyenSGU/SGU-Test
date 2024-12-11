document.getElementById("selectExam").addEventListener("change",function(event){
    loadAnalyst(event.target.selectedIndex)
})
document.getElementById("png").addEventListener("click",function(event){
    exportPNG();
})
document.getElementById("excel").addEventListener("click",function(event){
    exportExcel();
})
let dataxlxs = [];
function exportPNG(){
        const canvas = document.getElementById("myChart")
        // Lấy URL của hình ảnh từ nội dung của canvas
        const dataURL = canvas.toDataURL();
        // Tạo một phần tử a để tạo một liên kết tải xuống
        const link = document.createElement('a');
        link.href = dataURL;
        link.download = 'ketquathi.png'; // Đặt tên cho tệp ảnh được tải xuống
        link.click(); // Tự động kích hoạt sự kiện click để tải xuống
}
function exportExcel(){
    // Tạo một workbook mới
    const workbook = XLSX.utils.book_new();

    // Tạo một worksheet từ dữ liệu
    const worksheet = XLSX.utils.aoa_to_sheet(dataxlxs);

    // Thêm worksheet vào workbook
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1');

    // Xuất workbook thành một file Excel
    XLSX.writeFile(workbook, 'ketquathi.xlsx');

}
function loadAnalyst (index) {
    var selectElement = document.getElementById("selectExam");
        var firstOption = selectElement.options[index];
        var url = "http://localhost:9999/Project/studentSearch/result";
        var subjectIDlabels = []
        var subjectNamelabels = []
        var pointDatasets = []
        const params = {
            ExamID:firstOption.value,
            StudentID:(JSON.parse(localStorage.getItem("info"))).StudentID
        }
        dataxlxs = [];
        dataxlxs.push(["Mã Môn Học","Tên Môn Học","Điểm"])
        fetch(url, {
            method: 'POST',
            headers: {

                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params)
        })
        .then(response => response.json())
        .then(data => {
                // Duyệt qua mảng dữ liệu và thêm từng phần tử vào thẻ div
                const fetchPromises = data.data.map(item => {
                    subjectIDlabels.push(item.SubjectID)
                    subjectNamelabels.push(item.SubjectName)
                        pointDatasets.push(item.TotalPoint)
                        dataxlxs.push([item.SubjectID,item.SubjectName,item.TotalPoint])
                });
                // Đợi cho tất cả các cuộc gọi fetch hoàn thành trước khi tiếp tục
                Promise.all(fetchPromises).then(() => {
                    // Tất cả các dữ liệu đã được tải xong
                    // Kiểm tra xem có biểu đồ nào đang tồn tại trên canvas có ID là 'myChart' không
                    const existingChart = Chart.getChart(document.getElementById('myChart'));

                    if (existingChart) {
                        existingChart.destroy();
                    }
                    const ctx = document.getElementById('myChart').getContext('2d');
                    const plugin = {
                      id: 'customCanvasBackgroundColor',
                      beforeDraw: (chart, args, options) => {
                        const {ctx} = chart;
                        ctx.save();
                        ctx.globalCompositeOperation = 'destination-over';
                        ctx.fillStyle = options.color || '#99ffff';
                        ctx.fillRect(0, 0, chart.width, chart.height);
                        ctx.restore();
                      }
                    };
                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels:subjectIDlabels,
                            datasets: [{
                                label:"Tên môn học : ",
                                data: pointDatasets,
                                borderWidth: 1,
                                barThickness: 20,
                            }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    max: 10 // Giá trị cao nhất trên trục y là 10
                                }
                            },
                            plugins: {
                                customCanvasBackgroundColor: {
                                        color: '#ffffff',
                                },
                                legend: {
                                    display:false,
                                    hidden: true, // Ẩn ô bấm ẩn các cột
                                },
                                tooltip: {
                                    callbacks: {
                                        title: function(context) {
                                            const index = context[0].dataIndex;
                                            return `Mã môn học : ${subjectIDlabels[index]}`;
                                        },
                                        label: function(context) {
                                            let label = context.dataset.label || '';
                                            if (label) {
                                                label += `${subjectNamelabels[context.dataIndex]} , Điểm:${pointDatasets[context.dataIndex]}`;
                                            }
                                            return label;
                                        }
                                    }
                                }
                            }
                        },
                        plugins: [plugin],
                    });
                });
        });
}
window.onload = function(){
    loadAnalyst(0);
}