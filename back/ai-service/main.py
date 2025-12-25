from fastapi import FastAPI
from pydantic import BaseModel
from sentence_transformers import SentenceTransformer
from langdetect import detect



app = FastAPI(title="AI Enrichment Service")

# Load once at startup
embedding_model = SentenceTransformer(
    "sentence-transformers/all-MiniLM-L6-v2"
)

# ---------- API Models ----------

class EnrichRequest(BaseModel):
    text: str

class EnrichResponse(BaseModel):
    # summary: str
    embedding: list[float]
    language: str
    
# ---------- helpers ----------
def detect_language_safe(text: str) -> str:
    try:
        return detect(text)
    except Exception:
        return "unknown"
      

# ---------- Endpoint ----------

@app.post("/enrich", response_model=EnrichResponse)
def enrich(req: EnrichRequest):
    embedding = embedding_model.encode(
        req.text,
        normalize_embeddings=True
    )

    return EnrichResponse(
        embedding=embedding.tolist(),
        language=detect_language_safe(req.text)
    )
    
