DROP DATABASE quan_ly_thi_trac_nghiem;
CREATE DATABASE quan_ly_thi_trac_nghiem;
USE quan_ly_thi_trac_nghiem;

CREATE TABLE
    employee
(
    EmployeeID  INT UNSIGNED NOT NULL AUTO_INCREMENT,
    FirstName   VARCHAR(255) NOT NULL,
    LastName    VARCHAR(255) NOT NULL,
    Phone       VARCHAR(20) NOT NULL,
    Email       VARCHAR(255) NOT NULL,
    IsDeleted   BOOLEAN DEFAULT FALSE,
    DateOfBirth DATE NOT NULL,
    Gender      BOOLEAN NOT NULL,
    Password    VARCHAR(255) NOT NULL,
    RoleID      SMALLINT UNSIGNED,
    MajorID     SMALLINT UNSIGNED,
    PRIMARY KEY (EmployeeID)
);

INSERT INTO
    employee
(
    EmployeeID,
    FirstName,
    LastName,
    Phone,
    Email,
    IsDeleted,
    DateOfBirth,
    Gender,
    Password,
    RoleID,
    MajorID
)
VALUES
    (
        1,
        'Hoàng',
        'Phạm',
        '1111111111',
        'hoang.pham@example.com',
        false,
        '1980-01-01',
        true,
        'hoang_password',
        1,
        NULL
    );
INSERT INTO
    employee
(
    EmployeeID,
    FirstName,
    LastName,
    Phone,
    Email,
    IsDeleted,
    DateOfBirth,
    Gender,
    Password,
    RoleID,
    MajorID
)
VALUES
    (
        2,
        'Lan',
        'Võ',
        '2222222222',
        'lan.vo@example.com',
        false,
        '1985-01-01',
        false,
        'lan_password',
        2,
        1
    );
INSERT INTO
    employee
(
    EmployeeID,
    FirstName,
    LastName,
    Phone,
    Email,
    IsDeleted,
    DateOfBirth,
    Gender,
    Password,
    RoleID,
    MajorID
)
VALUES
    (
        3,
        'Nam',
        'Nguyễn',
        '3333333333',
        'nam.nguyen@example.com',
        false,
        '1990-01-01',
        true,
        'nam_password',
        1,
        2
    );
INSERT INTO
    employee
(
    EmployeeID,
    FirstName,
    LastName,
    Phone,
    Email,
    IsDeleted,
    DateOfBirth,
    Gender,
    Password,
    RoleID,
    MajorID
)
VALUES
    (
        4,
        'Hoàng',
        'Phạm',
        '1111111111',
        'hoang.pham@example.com',
        false,
        '1980-01-01',
        true,
        'hoang_password',
        1,
        NULL
    );

CREATE TABLE
    exam
(
    ExamID    INT UNSIGNED NOT NULL AUTO_INCREMENT,
    Name      VARCHAR(255) NOT NULL,
    DateStart DATETIME NOT NULL,
    DateEnd   DATETIME NOT NULL,
    Status enum('NOT_STARTED','IN_PROGRESS','COMPLETED') NOT NULL,
    IsDeleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (ExamID)
);

INSERT INTO
    exam
(
    ExamID,
    Name,
    DateStart,
    DateEnd,
    Status,
    IsDeleted
)
VALUES
    (
        1,
        'Kiểm tra giữa kì',
        '2024-04-01 00:00:00',
        '2024-04-19 00:00:00',
        'COMPLETED',
        false
    );
INSERT INTO
    exam
(
    ExamID,
    Name,
    DateStart,
    DateEnd,
    Status,
    IsDeleted
)
VALUES
    (
        2,
        'Kiểm tra cuối kỳ',
        '2024-05-15 13:00:00',
        '2024-05-15 15:00:00',
        'NOT_STARTED',
        false
    );

CREATE TABLE
    major
(
    MajorID     SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    IsDeleted   BOOLEAN DEFAULT false,
    Name        VARCHAR(255) NOT NULL,
    Description text,
    PRIMARY KEY (MajorID)
);

INSERT INTO
    major
(
    MajorID,
    IsDeleted,
    Name,
    Description
)
VALUES
    (
        1,
        false,
        'Toán - Tin học',
        'Mô tả về khoa Toán - Tin học'
    );
INSERT INTO
    major
(
    MajorID,
    IsDeleted,
    Name,
    Description
)
VALUES
    (
        2,
        false,
        'Kỹ thuật Điện - Điện tử',
        'Mô tả về khoa Kỹ thuật Điện - Điện tử'
    );
INSERT INTO
    major
(
    MajorID,
    IsDeleted,
    Name,
    Description
)
VALUES
    (
        3,
        false,
        'Khoa học Tự nhiên',
        'Mô tả về khoa Khoa học Tự Nhiên'
    );
CREATE TABLE
    `option`
(
    OptionID   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    QuestionID INT UNSIGNED,
    Title      VARCHAR(255),
    IsDeleted  BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (OptionID)
);
CREATE TABLE Question (
                          QuestionID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                          Title NVARCHAR(255) NOT NULL,
                          Point DECIMAL(5,2) CHECK (Point >= 0) NOT NULL,
                          CorrectAnswer INT UNSIGNED NOT NULL,
                          EmployeeID INT UNSIGNED,
                          SubjectID INT UNSIGNED NOT NULL,
                          Level ENUM('EASY','MEDIUM','HARD') NOT NULL,
                          FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID),
                          FOREIGN KEY (SubjectID) REFERENCES Subject(SubjectID)
);

CREATE TABLE `Option` (
                          OptionID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                          QuestionID INT UNSIGNED,
                          Title NVARCHAR(255) NOT NULL,
                          FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID)
);
CREATE TABLE TestDetails (
                             TestID INT UNSIGNED NOT NULL,
                             QuestionID INT UNSIGNED NOT NULL,
                             FOREIGN KEY (TestID) REFERENCES Test(TestID),
                             FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID),
                             CONSTRAINT unique_test_per_question UNIQUE (TestID, QuestionID)
);
CREATE TABLE ResultDetails (
                               ResultID INT UNSIGNED NOT NULL,
                               QuestionID INT UNSIGNED NOT NULL,
                               CorrectAnswer INT UNSIGNED NOT NULL,
                               CorrectAnswerTitle NVARCHAR(255),
                               ChooseOption INT UNSIGNED NOT NULL,
                               ChooseOptionTitle NVARCHAR(255),
                               Point DECIMAL(5,2) CHECK (Point >= 0) NOT NULL,
                               FOREIGN KEY (ResultID) REFERENCES Result(ResultID),
                               FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID),
                               CONSTRAINT unique_result_per_question UNIQUE (ResultID, QuestionID)
);

-- Inserting data into `Role` table
INSERT INTO Role (Name, Description) VALUES
                                         ('Giảng viên', 'Role dành cho giảng viên'),
                                         ('Admin', 'Role dành cho quản trị viên');

INSERT INTO
    `option`
(
    OptionID,
    QuestionID,
    Title,
    IsDeleted
)
VALUES
    (
        1,
        1,
        'Vật ở trạng thái nghỉ thì giữ nguyên trạng thái, vật ở trạng thái chuyển động thì giữ nguyên tốc độ và hướng chuyển động cho đến khi bị một lực không cân bằng ảnh hưởng.'
        ,
        0
    );
INSERT INTO
    `option`
(
    OptionID,
    QuestionID,
    Title,
    IsDeleted
)
VALUES
    (
        2,
        1,
        'Lực bằng khối lượng nhân gia tốc.',
        0
    );
INSERT INTO
    `option`
(
    OptionID,
    QuestionID,
    Title,
    IsDeleted
)
VALUES
    (
        3,
        1,
        'Với mỗi hành động, có một phản ứng tương đương và trái chiều.',
        0
    );
INSERT INTO
    `option`
(
    OptionID,
    QuestionID,
    Title,
    IsDeleted
)
VALUES
    (
        4,
        1,
        'Không có phản ứng nào đúng',
        0
    );
INSERT INTO
    `option`
(
    OptionID,
    QuestionID,
    Title,
    IsDeleted
)
VALUES
    (
        5,
        2,
        'Gồ ghề đôi',
        0
    );
INSERT INTO
    `option`
(
    OptionID,
    QuestionID,
    Title,
    IsDeleted
)
VALUES
    (
        6,
        2,
        'Gồ ghề đơn',
        0
    );
INSERT INTO
    `option`
(
    OptionID,
    QuestionID,
    Title,
    IsDeleted
)
VALUES
    (
        7,
        2,
        'Gồ ghề ba',
        0
    );
INSERT INTO
    `option`
(
    OptionID,
    QuestionID,
    Title,
    IsDeleted
)
VALUES
    (
        8,
        2,
        'Không gồ ghề',
        0
    );
CREATE TABLE
    question
(
    QuestionID    INT UNSIGNED NOT NULL AUTO_INCREMENT,
    Title         VARCHAR(255),
    Point         DECIMAL(5,2) NOT NULL,
    CorrectAnswer INT UNSIGNED,
    EmployeeID    INT UNSIGNED,
    SubjectID     INT UNSIGNED NOT NULL,
    `Level` enum('EASY','MEDIUM','HARD') NOT NULL,
    IsDeleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (QuestionID)
);

INSERT INTO
    question
(
    QuestionID,
    Title,
    Point,
    CorrectAnswer,
    EmployeeID,
    SubjectID,
    `Level`,
    IsDeleted
)
VALUES
    (
        1,
        'Định luật Newton thứ nhất là gì?',
        10.00,
        1,
        2,
        1,
        'HARD',
        0
    );
INSERT INTO
    question
(
    QuestionID,
    Title,
    Point,
    CorrectAnswer,
    EmployeeID,
    SubjectID,
    `Level`,
    IsDeleted
)
VALUES
    (
        2,
        'Cấu trúc của DNA như thế nào?',
        10.00,
        6,
        2,
        2,
        'MEDIUM',
        0
    );
CREATE TABLE
    `result`
(
    ResultID       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    StudentID      INT UNSIGNED NOT NULL,
    TestID         INT UNSIGNED NOT NULL,
    TotalCorrect   BOOLEAN NOT NULL,
    TotalIncorrect BOOLEAN NOT NULL,
    TotalPoint     DECIMAL(5,2) DEFAULT 0.00,
    Status enum('PENDING','IN_PROGRESS','COMPLETED') NOT NULL,
    DateStartParticipate DATETIME,
    DateEndParticipate   DATETIME,
    PRIMARY KEY (ResultID),
    CONSTRAINT unique_exam_per_student UNIQUE (StudentID, TestID)
);

INSERT INTO
    `result`
(
    ResultID,
    StudentID,
    TestID,
    TotalCorrect,
    TotalIncorrect,
    TotalPoint,
    Status,
    DateStartParticipate,
    DateEndParticipate
)
VALUES
    (
        1,
        1,
        1,
        1,
        0,
        10.00,
        'COMPLETED',
        '2024-03-15 07:31:00',
        '2024-03-16 08:50:00'
    );
INSERT INTO
    `result`
(
    ResultID,
    StudentID,
    TestID,
    TotalCorrect,
    TotalIncorrect,
    TotalPoint,
    Status,
    DateStartParticipate,
    DateEndParticipate
)
VALUES
    (
        2,
        2,
        2,
        1,
        0,
        10.00,
        'COMPLETED',
        '2024-03-15 07:30:50',
        '2024-03-15 09:00:00'
    );
CREATE TABLE
    resultdetails
(
    ResultID           INT UNSIGNED NOT NULL,
    BackupQuestionID   INT UNSIGNED NOT NULL,
    CorrectAnswer      INT UNSIGNED NOT NULL,
    CorrectAnswerTitle VARCHAR(255),
    ChooseOption       INT UNSIGNED NOT NULL,
    ChooseOptionTitle  VARCHAR(255),
    Point              DECIMAL(5,2) NOT NULL,
    CONSTRAINT unique_resultdetails UNIQUE (ResultID, BackupQuestionID)
);

INSERT INTO
    resultdetails
(
    ResultID,
    BackupQuestionID,
    CorrectAnswer,
    CorrectAnswerTitle,
    ChooseOption,
    ChooseOptionTitle,
    Point
)
VALUES
    (
        1,
        1,
        1,
        'Vật ở trạng thái nghỉ thì giữ nguyên trạng thái, vật ở trạng thái chuyển động thì giữ nguyên tốc độ và hướng chuyển động cho đến khi bị một lực không cân bằng ảnh hưởng.'
        ,
        1,
        'Vật ở trạng thái nghỉ thì giữ nguyên trạng thái, vật ở trạng thái chuyển động thì giữ nguyên tốc độ và hướng chuyển động cho đến khi bị một lực không cân bằng ảnh hưởng.'
        ,
        10.00
    );
INSERT INTO
    resultdetails
(
    ResultID,
    BackupQuestionID,
    CorrectAnswer,
    CorrectAnswerTitle,
    ChooseOption,
    ChooseOptionTitle,
    Point
)
VALUES
    (
        2,
        2,
        5,
        'Gồ ghề đôi',
        5,
        'Gồ ghề đôi',
        10.00
    );
CREATE TABLE
    `role`
(
    RoleID      SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    Name        VARCHAR(255),
    Description text,
    PRIMARY KEY (RoleID)
);

INSERT INTO
    `role`
(
    RoleID,
    Name,
    Description
)
VALUES
    (
        1,
        'Giảng viên',
        'Role dành cho giảng viên'
    );
INSERT INTO
    `role`
(
    RoleID,
    Name,
    Description
)
VALUES
    (
        2,
        'Admin',
        'Role dành cho quản trị viên'
    );
CREATE TABLE
    student
(
    StudentID   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    FirstName   VARCHAR(255),
    LastName    VARCHAR(255),
    Phone       VARCHAR(20),
    Email       VARCHAR(255),
    Password    VARCHAR(255),
    IsDeleted   BOOLEAN DEFAULT FALSE,
    DateOfBirth DATE NOT NULL,
    Gender      BOOLEAN NOT NULL,
    MajorID     SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (StudentID)
);

INSERT INTO
    student
(
    StudentID,
    FirstName,
    LastName,
    Phone,
    Email,
    Password,
    IsDeleted,
    DateOfBirth,
    Gender,
    MajorID
)
VALUES
    (
        1,
        'Anh',
        'Nguyễn',
        '1234567890',
        'anh.nguyen@example.com',
        '12345',
        false,
        '2002-03-15',
        true,
        1
    );
INSERT INTO
    student
(
    StudentID,
    FirstName,
    LastName,
    Phone,
    Email,
    Password,
    IsDeleted,
    DateOfBirth,
    Gender,
    MajorID
)
VALUES
    (
        2,
        'Linh',
        'Trần',
        '9876543210',
        'linh.tran@example.com',
        '12345',
        false,
        '2003-05-20',
        false,
        2
    );
INSERT INTO
    student
(
    StudentID,
    FirstName,
    LastName,
    Phone,
    Email,
    Password,
    IsDeleted,
    DateOfBirth,
    Gender,
    MajorID
)
VALUES
    (
        3,
        'Thành',
        'Lê',
        '5555555555',
        'thanh.le@example.com',
        '12345',
        false,
        '2001-08-10',
        true,
        3
    );
INSERT INTO
    student
(
    StudentID,
    FirstName,
    LastName,
    Phone,
    Email,
    Password,
    IsDeleted,
    DateOfBirth,
    Gender,
    MajorID
)
VALUES
    (
        4,
        'Kiệt',
        'Mai',
        '0987654321',
        'kiet@dep.trai',
        '12345',
        false,
        '2004-01-01',
        true,
        1
    );

CREATE TABLE
    subject
(
    SubjectID   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    Name        VARCHAR(255),
    IsDeleted   BOOLEAN DEFAULT FALSE,
    Description text,
    MajorID     SMALLINT UNSIGNED,
    PRIMARY KEY (SubjectID)
);

INSERT INTO
    subject
(
    SubjectID,
    Name,
    IsDeleted,
    Description,
    MajorID
)
VALUES
    (
        1,
        'Toán cao cấp',
        false,
        'Môn học về toán cao cấp',
        1
    );
INSERT INTO
    subject
(
    SubjectID,
    Name,
    IsDeleted,
    Description,
    MajorID
)
VALUES
    (
        2,
        'Lập trình Java',
        false,
        'Môn học về lập trình Java',
        2
    );
INSERT INTO
    subject
(
    SubjectID,
    Name,
    IsDeleted,
    Description,
    MajorID
)
VALUES
    (
        3,
        'Vật lý cơ bản',
        false,
        'Môn học về vật lý cơ bản',
        3
    );
CREATE TABLE
    test
(
    TestID           INT UNSIGNED NOT NULL AUTO_INCREMENT,
    ExamID           INT UNSIGNED NOT NULL,
    SubjectID        INT UNSIGNED NOT NULL,
    EmployeeID       INT UNSIGNED,
    NumberOfQuestion SMALLINT UNSIGNED NOT NULL,
    TotalPoint       DECIMAL(5,2) DEFAULT 0.00,
    Duration         VARCHAR(100),
    DateStart        DATETIME NOT NULL,
    DateEnd          DATETIME NOT NULL,
    Status enum('NOT_STARTED','IN_PROGRESS','COMPLETED') NOT NULL,
    Description text,
    IsDeleted   BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (TestID)
);

INSERT INTO
    test
(
    TestID,
    ExamID,
    SubjectID,
    EmployeeID,
    NumberOfQuestion,
    TotalPoint,
    Duration,
    DateStart,
    DateEnd,
    Status,
    Description,
    IsDeleted
)
VALUES
    (
        1,
        2,
        3,
        3,
        1,
        10.00,
        '01:30:00',
        '2024-03-15 07:30:00',
        '2024-03-15 09:00:00',
        'COMPLETED',
        'Bài thi không được sử dụng tài liệu, sinh viên tham gia vào zoom bật camera đầy đủ để giám khảo theo dõi quá trình làm bài'
        ,
        0
    );
INSERT INTO
    test
(
    TestID,
    ExamID,
    SubjectID,
    EmployeeID,
    NumberOfQuestion,
    TotalPoint,
    Duration,
    DateStart,
    DateEnd,
    Status,
    Description,
    IsDeleted
)
VALUES
    (
        2,
        2,
        2,
        2,
        1,
        10.00,
        '01:30:00',
        '2024-03-15 07:30:00',
        '2024-03-15 09:00:00',
        'COMPLETED',
        'Bài thi được sử dụng tài liệu giấy, sách',
        0
    );
INSERT INTO
    test
(
    TestID,
    ExamID,
    SubjectID,
    EmployeeID,
    NumberOfQuestion,
    TotalPoint,
    Duration,
    DateStart,
    DateEnd,
    Status,
    Description,
    IsDeleted
)
VALUES
    (
        3,
        2,
        1,
        3,
        1,
        10.00,
        '01:30:00',
        '2024-03-15 07:30:00',
        '2024-03-15 09:00:00',
        'COMPLETED',
        'Bài thi không được sử dụng tài liệu, sinh viên tham gia vào zoom bật camera đầy đủ để giám khảo theo dõi quá trình làm bài'
        ,
        0
    );
CREATE TABLE
    testdetails
(
    TestID           INT UNSIGNED NOT NULL,
    BackupQuestionID INT UNSIGNED NOT NULL,
    CONSTRAINT unique_testdetails UNIQUE (TestID, BackupQuestionID)
);

INSERT INTO
    testdetails
(
    TestID,
    BackupQuestionID
)
VALUES
    ('Anh', 'Nguyễn','12345',0, '1234567890', 'anh.nguyen@example.com', '2002-03-15', 1, 1),
    ('Linh', 'Trần','12345',0, '9876543210', 'linh.tran@example.com', '2003-05-20', 0, 2),
    ('Thành', 'Lê','12345',0, '5555555555', 'thanh.le@example.com', '2001-08-10', 1, 3),
    ('Kiệt', 'Mai Trần Tuấn','12345',0, '09999', 'kiet.dep@trai.com', '2004-03-15', 1, 1);

-- Inserting more data into `Employee` table
INSERT INTO `Employee` (FirstName, LastName, Isdeleted, Phone, Email, DateOfBirth, Gender, Password, RoleID, MajorID)
VALUES
    (
        12,
        1,
        'Không có phản ứng nào đúng'
    );
INSERT INTO
    backupoption
(
    OptionID,
    BackupQuestionID,
    Title
)
VALUES
    (
        13,
        2,
        'Gồ ghề đôi'
    );
INSERT INTO
    backupoption
(
    OptionID,
    BackupQuestionID,
    Title
)
VALUES
    (
        14,
        2,
        'Gồ ghề đơn'
    );
INSERT INTO
    backupoption
(
    OptionID,
    BackupQuestionID,
    Title
)
VALUES
    (
        15,
        2,
        'Gồ ghề ba'
    );
INSERT INTO
    backupoption
(
    OptionID,
    BackupQuestionID,
    Title
)
VALUES
    (
        16,
        2,
        'Không gồ ghề'
    );
CREATE TABLE
    backupquestion
(
    BackupQuestionID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    QuestionID       INT UNSIGNED,
    Title            VARCHAR(255),
    Point            DECIMAL(5,2) NOT NULL,
    CorrectAnswer    INT UNSIGNED NOT NULL,
    PRIMARY KEY (BackupQuestionID)
);

INSERT INTO
    backupquestion
(
    BackupQuestionID,
    QuestionID,
    Title,
    Point,
    CorrectAnswer
)
VALUES
    (
        1,
        1,
        'Định luật Newton thứ nhất là gì?',
        10.00,
        1
    );
INSERT INTO
    backupquestion
(
    BackupQuestionID,
    QuestionID,
    Title,
    Point,
    CorrectAnswer
)
VALUES
    (1, 1, 5, 'Gồ ghề đôi', 5, 'Gồ ghề đôi', 10.00),
    (2, 1, 5, 'Gồ ghề đôi', 5, 'Gồ ghề đôi', 10.00);


-- Cập nhật database mới nhất

CREATE TABLE IF NOT EXISTS BackupQuestion (
	BackupQuestionID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    QuestionID INT UNSIGNED,
    Title NVARCHAR(255) NOT NULL,
    Point DECIMAL(5,2) CHECK (Point >= 0) NOT NULL,
    CorrectAnswer INT UNSIGNED NOT NULL,
    FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID)
);

CREATE TABLE IF NOT EXISTS BackupOption (
                          OptionID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                          BackupQuestionID INT UNSIGNED,
                          Title NVARCHAR(255) NOT NULL,
                          FOREIGN KEY (BackupQuestionID) REFERENCES BackupQuestion(BackupQuestionID)
);
ALTER TABLE Question DROP COLUMN Isdeleted;

ALTER TABLE `Option`
MODIFY COLUMN QuestionID INT UNSIGNED NULL

ALTER TABLE TestDetails
CHANGE COLUMN QuestionID BackupQuestionID INT UNSIGNED NOT NULL;
-- Thay tên khóa TestDetails_ibfk_1 và TestDetails_ibfk_2 thành 2 khóa ngoại tương ứng bên database mỗi máy nhé
ALTER TABLE TestDetails DROP FOREIGN KEY TestDetails_ibfk_1;
ALTER TABLE TestDetails DROP FOREIGN KEY TestDetails_ibfk_2;

ALTER TABLE TestDetails
DROP CONSTRAINT unique_test_per_question

ALTER TABLE TestDetails DROP INDEX unique_test_per_question;

ALTER TABLE TestDetails
ADD FOREIGN KEY (TestID)
REFERENCES Test(TestID);

INSERT INTO BackupQuestion (Title, Point, CorrectAnswer, QuestionID)
VALUES
    ('Định luật Newton thứ nhất là gì?',10.00,1,1),
    ('Cấu trúc của DNA như thế nào?',10.00,2, 2);

INSERT INTO BackupOption (BackupQuestionID, Title)
VALUES
    (1, 'Vật ở trạng thái nghỉ thì giữ nguyên trạng thái, vật ở trạng thái chuyển động thì giữ nguyên tốc độ và hướng chuyển động cho đến khi bị một lực không cân bằng ảnh hưởng.'),
    (1, 'Lực bằng khối lượng nhân gia tốc.'),
    (1, 'Với mỗi hành động, có một phản ứng tương đương và trái chiều.'),
    (1, 'Không có phản ứng nào đúng'),
    (2, 'Gồ ghề đôi'),
    (2, 'Gồ ghề đơn'),
    (2, 'Gồ ghề ba'),
    (2, 'Không gồ ghề');

ALTER TABLE TestDetails
ADD FOREIGN KEY (BackupQuestionID)
REFERENCES BackupQuestion(BackupQuestionID);

ALTER TABLE TestDetails
ADD CONSTRAINT unique_testdetails UNIQUE (TestID, BackupQuestionID);

ALTER TABLE ResultDetails
CHANGE COLUMN QuestionID BackupQuestionID INT UNSIGNED NOT NULL;

ALTER TABLE ResultDetails DROP FOREIGN KEY ResultDetails_ibfk_1;
ALTER TABLE ResultDetails DROP FOREIGN KEY ResultDetails_ibfk_2;

ALTER TABLE ResultDetails DROP INDEX unique_result_per_question;

ALTER TABLE ResultDetails
ADD FOREIGN KEY (ResultID)
REFERENCES Result(ResultID);

ALTER TABLE ResultDetails
ADD FOREIGN KEY (BackupQuestionID)
REFERENCES BackupQuestion(BackupQuestionID);

ALTER TABLE ResultDetails
ADD CONSTRAINT unique_resultdetails UNIQUE (ResultID, BackupQuestionID);

DELIMITER //
CREATE PROCEDURE updateExamAndTest()
BEGIN
    -- Lấy thời gian hiện tại
    DECLARE current_time DATETIME;
    SET current_time = NOW();

    -- Cập nhật trạng thái của các kỳ thi
    UPDATE Exam
    SET Status = CASE
                    WHEN DATE_FORMAT(DateStart, '%Y-%m-%d %H:%i') <= DATE_FORMAT(current_time, '%Y-%m-%d %H:%i') AND DATE_FORMAT(current_time, '%Y-%m-%d %H:%i')  < DATE_FORMAT(DateEnd, '%Y-%m-%d %H:%i') THEN 'IN_PROGRESS'
                    WHEN DATE_FORMAT(DateEnd, '%Y-%m-%d %H:%i') <= DATE_FORMAT(current_time, '%Y-%m-%d %H:%i') THEN 'COMPLETED'
                    ELSE 'NOT_STARTED'
                END;

    -- Cập nhật trạng thái của các bài thi
    UPDATE Test
    SET Status = CASE
                    WHEN DateStart <= current_time AND current_time < DateEnd AND Status <> 'IN_PROGRESS' THEN 'IN_PROGRESS'
                    WHEN DateEnd <= current_time AND Status <> 'COMPLETED' THEN 'COMPLETED'
                    ELSE 'NOT_STARTED'
                END
    WHERE Status <> 'IN_PROGRESS' OR (DateStart <= current_time AND current_time < DateEnd);
END;
//
DELIMITER ;

CREATE EVENT update_exam_and_test_event
ON SCHEDULE EVERY 1 MINUTE -- Hoặc bất kỳ khoảng thời gian nào bạn muốn
DO
BEGIN
    CALL updateExamAndTest();
END;
