from flask import Flask, request, jsonify
import tensorflow as tf
import numpy as np
from PIL import Image
import io

app = Flask(__name__)

model = tf.keras.models.load_model("model/traffic_sign_model.keras")

def preprocess_image(image_bytes):
    image = Image.open(io.BytesIO(image_bytes)).convert("RGB")
    image = image.resize((30, 30))
    image_array = np.array(image) / 255.0
    image_array = image_array.reshape((1, 30, 30, 3))
    return image_array

@app.route('/predict', methods=['POST'])
def predict():
    if 'file' not in request.files:
        return jsonify({'error': 'No file provided'}), 400

    file = request.files['file']
    image_bytes = file.read()
    try:
        input_tensor = preprocess_image(image_bytes)
        predictions = model.predict(input_tensor)
        predicted_class = int(np.argmax(predictions))
        return jsonify({'predicted_class': predicted_class})
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
