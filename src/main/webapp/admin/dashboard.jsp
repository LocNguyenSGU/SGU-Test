<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard</title>
    <!-- ======= Styles ====== -->
    <link rel="stylesheet" href="assets/css/style.css" />

  </head>

  <body>
    <!-- <a href="testdb">testdb</a>
  <a href="instructor/student">Kiet test Student</a>
  <a href="instructor/question">Kiet test Question</a>
  <a href="instructor/test">Kiet test Test</a> -->
    <!-- <div id="searchQuery"></div> -->
    <div class="container">
      <!-- =============== Navigation ================ -->
      <div class="navigation">
                        <ul>
                          <li>
                            <a href="#" style="display:flex;align-items:center;">
                                <span style="width:45px;height:45px;margin-left:25%">
                                    <img style="width:100%;" src="../assets/imgs/SGU-LOGO.png"/>
                                </span>
                              <span class="title" style="font-size:20px;">SGU</span>
                            </a>
                          </li>

                          <li class="active">
                            <a href="/Project/admin/dashboard">
                              <span class="icon">
                                <ion-icon name="home-outline"></ion-icon>
                              </span>
                              <span class="title">Tổng quan</span>
                            </a>
                          </li>
                <li>
                                                                    <a href="/Project/admin/employee">
                                                                      <span class="icon">
                                                                        <i class="fa-solid fa-chalkboard-user" style="font-size: 25px"></i>
                                                                      </span>
                                                                      <span class="title">Giảng Viên</span>
                                                                    </a>
                                                         </li>
                                              <li>
                                                              <a href="/Project/admin/subject">
                                                                      <span class="icon">
                                                                        <i class="fa-solid fa-book" style="font-size: 25px;"></i>
                                                                      </span>
                                                                      <span class="title">Môn học</span>
                                                                    </a>
                                                         </li>
                                                        <li>
                                                          <a href="/Project/admin/student">
                                                            <span class="icon">
                                                              <ion-icon name="people-outline"></ion-icon>
                                                            </span>
                                                            <span class="title">Học Sinh</span>
                                                          </a>
                                                        </li>
                                              <li>
                                                <a href="/Project/admin/exam">
                                                  <span class="icon">
                                                    <ion-icon name="chatbubble-outline"></ion-icon>
                                                  </span>
                                                  <span class="title">Kỳ thi</span>
                                                </a>
                                              </li>
                                              <li>
                                                <a href="/Project/admin/test">
                                                  <span class="icon">
                                                    <i class="fa-regular fa-file-lines" style="font-size: 25px"></i>
                                                  </span>
                                                  <span class="title">Bài thi</span>
                                                </a>
                                              </li>
                                               <li>
                                                 <a href="/Project/admin/thongketheophodiem">
                                                   <span class="icon">
                                                     <i class="fa-solid fa-chart-simple" style="font-size: 25px;"></i>
                                                   </span>
                                                   <span class="title">Phổ điểm</span>
                                                 </a>
                                               </li>
                                              <li>
                                                <a href="/Project/admin/questionbank">
                                                  <span class="icon">
                                                    <ion-icon name="help-outline"></ion-icon>
                                                  </span>
                                                  <span class="title">Ngân hàng câu hỏi</span>
                                                </a>
                                              </li>
                                              <li>
                                                  <a href="/Project/admin/major">
                                                    <span class="icon">
                                                    <i class="fa-solid fa-layer-group"></i>
                                                     </span>
                                                     <span class="title">Chuyên ngành</span>
                                                          </a>
                                                     </li>

                                              <li>
                                                <a id="logout">
                                                  <span class="icon">
                                                    <ion-icon name="log-out-outline"></ion-icon>
                                                  </span>
                                                  <span class="title">Đăng xuất</span>
                                                </a>
                                              </li>
                                            </ul>
      </div>



      <!-- ========================= Main ==================== -->
      <div class="main">
        <div class="topbar">
          <div class="toggle">
            <ion-icon name="menu-outline"></ion-icon>
          </div>

          <!-- <div class="search"> -- đừng xoá nhen, có thể dùng ở phần sau
                    <label>
                        <input type="text" placeholder="Search here">
                        <ion-icon name="search-outline"></ion-icon>
                    </label>
            </div> -->

          <div class="hello">
            <div class="user">
              <img src="assets/imgs/lecture.jpg" alt="" />
            </div>
            <p>Hi, lecture</p>
          </div>
        </div>

        <!-- ======================= Cards ================== -->
        <div class="cardBox">
          <div class="card">
            <div>
              <div class="numbers">${numberTest}</div>
              <div class="cardName">Bài thi</div>
            </div>

            <div class="iconBx">
              <ion-icon name="eye-outline"></ion-icon>
            </div>
          </div>

          <div class="card">
            <div>
              <div class="numbers">${numberStudent}</div>
              <div class="cardName">Học sinh</div>
            </div>

            <div class="iconBx">
              <ion-icon name="people-outline"></ion-icon>
            </div>
          </div>
          <div class="card">
            <div>
              <div class="numbers">${numberEmployee}</div>
              <div class="cardName">Giáo viên</div>
            </div>

            <div class="iconBx">
              <i class="fa-solid fa-chalkboard-user"></i>
            </div>
          </div>

          <div class="card">
            <div>
              <div class="numbers">${numberQuestion}</div>
              <div class="cardName">Câu hỏi</div>
            </div>

            <div class="iconBx">
              <ion-icon name="help-outline"></ion-icon>
            </div>
          </div>
        </div>

        <!-- ================ Test List && Diagram ================= -->
        <div class="details">
          <div class="recentTests hidden">
            <div class="cardHeader">
              <h2>Recent Test</h2>
              <a href="#" class="btn">View All</a>
            </div>

            <table>
              <thead>
                <tr>
                  <td>Name</td>
                  <td>Duration</td>
                  <td>Start Date</td>
                  <td>Status</td>
                </tr>
              </thead>

              <tbody>
                <tr>
                  <td>Star Refrigerator</td>
                  <td>90 minuites</td>
                  <td>9h.50' 30/2/2023</td>

                  <td><span class="status notStart">Not started</span></td>
                </tr>

                <tr>
                  <td>Dell Laptop</td>
                  <td>120 minuites</td>
                  <td>9h.50' 30/2/2023</td>

                  <td><span class="status inProgress">In progress</span></td>
                </tr>

                <tr>
                  <td>Apple Watch</td>
                  <td>45 minuites</td>
                  <td>9h.50' 30/2/2023</td>

                  <td><span class="status complete">Completed</span></td>
                </tr>

                <tr>
                  <td>Addidas Shoes</td>
                  <td>60 minuites</td>
                  <td>9h.50' 30/2/2023</td>

                  <td><span class="status cancel">Cancelled</span></td>
                </tr>

                <tr>
                  <td>Star Refrigerator</td>
                  <td>90 minuites</td>
                  <td>9h.50' 30/2/2023</td>

                  <td><span class="status paused">Paused</span></td>
                </tr>

                <tr>
                  <td>Dell Laptop</td>
                  <td>120 minuites</td>
                  <td>9h.50' 30/2/2023</td>

                  <td><span class="status paused">Paused</span></td>
                </tr>

                <tr>
                  <td>Apple Watch</td>
                  <td>60 minuites</td>
                  <td>9h.50' 30/2/2023</td>

                  <td><span class="status paused">Paused</span></td>
                </tr>

                <tr>
                  <td>Addidas Shoes</td>
                  <td>30 minuites</td>
                  <td>9h.50' 30/2/2023</td>
                  <td><span class="status paused">Paused</span></td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- ================= Diagram ================ -->
          <div class="recentCustomers">
            <!-- <div class="cardHeader">
              <h2>Diagram result of test</h2>
            </div>
            <p>Diagram here</p> -->
        
            <div class="diagram">
              <label for="time-option">Biểu đồ cho</label>
              <select name="time-option" id="time-option">
                <option value="0" selected>Tất cả thời gian</option>
                <option value="7">7 ngày</option>
                <option value="30">30 ngày</option>
              </select>
              <canvas id="barChart"></canvas>
              <span class="desc-diagram"
                >Biểu đồ thống kê số lượng bài thi theo ngày</span
              >
              <div class="div-date">
                <input type="date" name="dateStart" id="dateStart" />
                <input type="date" name="dateEnd" id="dateEnd" />
                <button type="button filter-date" onclick="filterDate()">
                  Lọc theo ngày
                </button>
              </div>
              <div class="type-chart">
                <button
                  type="button"
                  onclick="renderBarChart('line')"
                >
                <i class="fa-solid fa-chart-line"></i>
                  Biểu đồ đường
                </button>
                <button
                  type="button"
                  onclick="renderBarChart()"
                >
                <i class="fa-solid fa-chart-simple"></i>
                  Biểu đồ cột
                </button>
              </div>
            </div>

            <div class="diagram">
              <canvas id="pieChart"></canvas>
              <span class="desc-diagram"
                >Biểu đồ thống kê số lượng từng loại câu hỏi</span
              >
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- =========== Scripts =========  -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
      var jsonDataPieChart = '${jsonDataPieChart}';
      var jsonDataBarChart = '${jsonDataBarChart}';
      console.log("jsonDataPieChart", jsonDataBarChart);
      var bar = document.getElementById("barChart").getContext("2d");
      var pie = document.getElementById("pieChart");
      var dataBarChartToDraw = [];
      var labelsBarChartToDraw = [];
      var dataPieChartToDraw = [];
      
      function parseJsonData() {
        labelsBarChartToDraw = [];        
          if (jsonDataBarChart) {
              var dataBarChart = JSON.parse(jsonDataBarChart);
              dataBarChartToDraw = dataBarChart.map(function (item) {
                  labelsBarChartToDraw.push(item.dateStart);
                  return item.number;
              });
          }
      
          if (jsonDataPieChart) {
              var dataPieChart = JSON.parse(jsonDataPieChart);
              dataPieChartToDraw = dataPieChart.map(function (item) {
                  return item.number;
              });
          }
      }
      var gradient = bar.createLinearGradient(0, 0, 0, 400);
gradient.addColorStop(0, 'rgba(89,116,204,1)');   
gradient.addColorStop(1, 'rgba(233,235,229,0.5648634453781513)');

      function renderBarChart(type) {
        if (window.myBarChart) {
          window.myBarChart.destroy();
        }
        window.myBarChart = new Chart(bar, {
          type: type || "bar",
          data: {
            labels: labelsBarChartToDraw,
            datasets: [{
              label: "Bài thi",
              data: dataBarChartToDraw,
              borderWidth: 1,
              fill: type === 'line' ? 'origin' : false,
              backgroundColor: type === 'line' ? gradient : null, // Use the gradient object here
              borderColor: type === 'line' ? 'blue' : null, // Màu đỏ của đường
              pointHoverBackgroundColor: type === 'line' ? '#fff' : null, // Màu đỏ khi hover vào điểm
              pointHoverBorderColor: type === 'line' ? 'blue' : null, // Màu đỏ của border khi hover vào điểm
              tension: 0.3,
              barThickness: 40
            }],
          },
          options: {
            scales: {
              y: {
                beginAtZero: true,
              },
            },
            plugins: {
              borderRadius: 100,
            }
          },
        });
      }
      
      function renderPieChart() {
        if (window.myPieChart) {
            window.myPieChart.destroy();
        }
        window.myPieChart = new Chart(pie, {
            type: "pie",
            backgroundColor: "red",
            data: {
                labels: ["Dễ", "Trung bình", "Khó"],
                datasets: [{
                    label: "số lượng câu hỏi",
                    data: dataPieChartToDraw,
                    borderWidth: 1,
                }],
            },
            options: {
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                var label = context.label || '';
                                var value = context.parsed || 0;
                                var total = context.dataset.data.reduce(function(acc, cur) {
                                    return acc + cur;
                                });
                                var percentage = Math.round((value / total) * 100);
                                return label + ': ' + value + ' (' + percentage + '%)';
                            }
                        }
                    }
                }
            },
        });
    }
    
    
      
      function renderOriginalCharts() {
          parseJsonData();
          renderBarChart();
          renderPieChart();
      }
      
      function filterDate() {
          var dateStart = document.getElementById("dateStart").value;
          var dateEnd = document.getElementById("dateEnd").value;
          var dataBarChart = JSON.parse(jsonDataBarChart);

          console.log("dateStart nhap vao", dateStart);
          console.log("dateStart:: ", new Date(dateStart));
          if (dateStart === "" || dateEnd === "") {
              renderOriginalCharts();
              return;
          }
      
          var dataBarChart = JSON.parse(jsonDataBarChart);
          console.log("du lieu data truoc khi loc: ", dataBarChart);
          var filteredData = dataBarChart.filter(function (item) {
            console.log("item.dateStart", new Date(item.dateStart));
              return new Date(formatDate(item.dateStart)) >= new Date(dateStart) && new Date(formatDate(item.dateStart)) <= new Date(dateEnd);
            });
          console.log("du lieu sau khi loc: ", filteredData)
          labelsBarChartToDraw = filteredData.map(function (item) {
              return item.dateStart;
          });
          dataBarChartToDraw = filteredData.map(function (item) {
              return item.number;
          });
          console.log("du lieu data de ve: ", dataBarChartToDraw)
          renderBarChart();
      }
      
      function formatDate(dateString) {
          var parts = dateString.split("-");
          return parts[2] + "-" + parts[1] + "-" + parts[0];
      }
      
      renderOriginalCharts();

      function filterTime() {
        var timeOption = document.getElementById("time-option").value;
        console.log("timeOption", timeOption);
        if(timeOption == 0) {
          var dataBarChart = JSON.parse(jsonDataBarChart);
          labelsBarChartToDraw = dataBarChart.map(function (item) {
            return item.dateStart;
        });
        dataBarChartToDraw = dataBarChart.map(function (item) {
            return item.number;
        });
          renderBarChart();
        } else if(timeOption == 7) {
          var dataBarChart = JSON.parse(jsonDataBarChart);
          console.log("du lieu data truoc khi loc: ", dataBarChart);
          let date = new Date();
          console.log("date hien tai", date);
          date.setDate(date.getDate() - 7);
          console.log("date 7 ngay truoc", date);

          var filteredData = dataBarChart.filter(function (item) {
            console.log("item.dateStart", new Date(formatDate(item.dateStart)));
              return new Date(formatDate(item.dateStart)) >= new Date(date);
            });
          console.log("du lieu sau khi loc: ", filteredData)
          labelsBarChartToDraw = filteredData.map(function (item) {
            return item.dateStart;
        });
        dataBarChartToDraw = filteredData.map(function (item) {
            return item.number;
        });
        renderBarChart();

        } else if(timeOption == 30) {
          var dataBarChart = JSON.parse(jsonDataBarChart);
          console.log("du lieu data truoc khi loc: ", dataBarChart);
          let date = new Date();
          console.log("date hien tai", date);
          date.setDate(date.getDate() - 30);
          console.log("date 30 ngay truoc", date);
          var filteredData = dataBarChart.filter(function (item) {
            console.log("item.dateStart", new Date(formatDate(item.dateStart)));
              return new Date(formatDate(item.dateStart)) >= new Date(date);
            });
          console.log("du lieu sau khi loc: ", filteredData)
          labelsBarChartToDraw = filteredData.map(function (item) {
            return item.dateStart;
        });
        dataBarChartToDraw = filteredData.map(function (item) {
            return item.number;
        });
          
        renderBarChart();
        }
      }
    document.getElementById("time-option").addEventListener("change", filterTime);
          
    </script>
        <script>
         document.getElementById("logout").addEventListener("click", function(event){
                if(window.confirm("Bạn có muốn đăng xuất ?")){
                    localStorage.removeItem('info');
                    fetch('http://localhost:9999/Project/logout',{
                       method:'POST',
                       headers:{
                         'Content-Type': 'application/json'
                     },
                     body: JSON.stringify({
                         'email': 'fkahsfkj',
                     })
                    })
                    window.location.href = "http://localhost:9999/Project/login"
                }
            })
        </script>
    <script src="assets/js/main.js"></script>

    <!-- ====== ionicons ======= -->
    <script
      src="https://kit.fontawesome.com/0dfb1263b2.js"
      crossorigin="anonymous"
    ></script>
    <script
      type="module"
      src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"
    ></script>
    <script
      nomodule
      src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"
    ></script>
  </body>
</html>
