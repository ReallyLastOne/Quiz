export interface PhraseResponse {
  phrases: Array<Phrase>
}

export interface Phrase {
  id: number;
  translations: { [key: string]: string };
}

export class PhraseTableEntry {
  id: number;
  pl: string;
  it: string;
  us: string;
  es: string;
  de: string;
}
