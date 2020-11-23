import cv2
import numpy as np

from files import generateFilename

net = cv2.dnn.readNet("yolov3_training_last.weights", "yolov3_testing.cfg")
object = "Carriage"
layer_names = net.getLayerNames()
output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]
colors = np.random.uniform(0, 255, size=1)

def getOpenCvImgByUrl(url):
    cap = cv2.VideoCapture(url)
    ret, img = cap.read()
    return img

def detect(url):
    img = getOpenCvImgByUrl(url)
    height, width, channels = img.shape

    #ДЕТЕКТ
    blob = cv2.dnn.blobFromImage(img, 0.00392, (416, 416), (0, 0, 0), True, crop=False)

    net.setInput(blob)
    outs = net.forward(output_layers)

    # ДОБАВЛЕНИЕ ИНФОРМАЦИИ НА КАРТИНКУ
    class_ids = []
    confidences = []
    boxes = []
    for out in outs:
        for detection in out:
            scores = detection[5:]
            class_id = np.argmax(scores)
            confidence = scores[class_id]
            if confidence > 0.3:
                # Детект объекта
                center_x = int(detection[0] * width)
                center_y = int(detection[1] * height)
                w = int(detection[2] * width)
                h = int(detection[3] * height)

                x = int(center_x - w / 2)
                y = int(center_y - h / 2)

                boxes.append([x, y, w, h])
                confidences.append(float(confidence))
                class_ids.append(class_id)

    indexes = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4)
    font = cv2.FONT_HERSHEY_PLAIN
    for i in range(len(boxes)):
        if i in indexes:
            x, y, w, h = boxes[i]
            label = object
            color = colors[class_ids[i]]
            cv2.rectangle(img, (x, y), (x + w, y + h), color, 2)
            cv2.putText(img, label, (x, y + 30), font, 3, color, 2)

    #Сохранение картинки
    path = "img/i" + generateFilename(url) + ".jpg"
    cv2.imwrite(path, img, [int(cv2.IMWRITE_JPEG_QUALITY), 90])
    return path