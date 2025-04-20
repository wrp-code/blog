export interface Article {
    id: string | null;
    createTime: string | null;
    title: string;
    content: string;
    userId: string | null;
    username: string;
    catalogId: string | null;
    hits: number | null;
}