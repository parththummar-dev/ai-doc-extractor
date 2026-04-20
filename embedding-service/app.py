from fastapi import FastAPI
from pydantic import BaseModel
from sentence_transformers import SentenceTransformer

app = FastAPI()

# Load model once (important)
model = SentenceTransformer("BAAI/bge-base-en-v1.5")

class TextRequest(BaseModel):
    text: str

@app.get("/")
def home():
    return {"message": "Embedding service is running"}

@app.post("/embed")
def get_embedding(request: TextRequest):
    text = "Represent this sentence for searching relevant passages: " + request.text
    embedding = model.encode(text).tolist()
    return {"embedding": embedding}