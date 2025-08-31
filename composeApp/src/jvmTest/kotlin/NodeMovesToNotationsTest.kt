import org.junit.Test
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.nodes.PGNHandler
import kotlin.test.assertEquals

class NodeMovesToNotationsTest {



    @Test
    fun testMovesNodesToChessNotation1(){
        val prueba1 =
            "MoveNode(id=root, parentId=null, childrenIds=[e5d1ce81-cef5-4ad7-914f-3294b57ad090], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                    "MoveNode(id=e5d1ce81-cef5-4ad7-914f-3294b57ad090, parentId=root, childrenIds=[db726c09-b47b-4a78-83d6-17e05d3d4cfc, e8af5d1a-8b3a-4842-9a67-67a6b1a50bd6, 8b0f2d12-c47d-4b65-9f73-0023dd066437, 55e3d034-d278-4cbe-b574-790f29896283], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=db726c09-b47b-4a78-83d6-17e05d3d4cfc, parentId=e5d1ce81-cef5-4ad7-914f-3294b57ad090, childrenIds=[b9504471-0847-4e20-b796-472befd17fda], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=b9504471-0847-4e20-b796-472befd17fda, parentId=db726c09-b47b-4a78-83d6-17e05d3d4cfc, childrenIds=[596a52ca-eaa7-4307-89cb-31b2ffe49d53], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 1 2, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=596a52ca-eaa7-4307-89cb-31b2ffe49d53, parentId=b9504471-0847-4e20-b796-472befd17fda, childrenIds=[6234ce93-6ed9-4e7d-a975-62c0d9785aee], san=Nc6, from=(1, 0), to=(2, 2), fen=r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 2 3, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=6234ce93-6ed9-4e7d-a975-62c0d9785aee, parentId=596a52ca-eaa7-4307-89cb-31b2ffe49d53, childrenIds=[], san=Bb5, from=(5, 7), to=(1, 3), fen=r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 3 3, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=e8af5d1a-8b3a-4842-9a67-67a6b1a50bd6, parentId=e5d1ce81-cef5-4ad7-914f-3294b57ad090, childrenIds=[5a677fab-5d38-4278-ada1-44017ab3ddf5, 68bb7117-3115-4aaf-b4ef-88cd71280499, f2ef9b98-7d0d-4f71-a196-a15f7188ddd6], san=e6, from=(4, 1), to=(4, 2), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=5a677fab-5d38-4278-ada1-44017ab3ddf5, parentId=e8af5d1a-8b3a-4842-9a67-67a6b1a50bd6, childrenIds=[34a26620-ef7d-455f-80fc-21a050d8b92d], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppp1ppp/4p3/8/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=34a26620-ef7d-455f-80fc-21a050d8b92d, parentId=5a677fab-5d38-4278-ada1-44017ab3ddf5, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp2ppp/4p3/3p4/3PP3/8/PPP2PPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=68bb7117-3115-4aaf-b4ef-88cd71280499, parentId=e8af5d1a-8b3a-4842-9a67-67a6b1a50bd6, childrenIds=[bdaeb5e5-ddfb-4663-8798-45fe827d29ba], san=d3, from=(3, 6), to=(3, 5), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/3P4/PPP2PPP/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=bdaeb5e5-ddfb-4663-8798-45fe827d29ba, parentId=68bb7117-3115-4aaf-b4ef-88cd71280499, childrenIds=[], san=d6, from=(3, 1), to=(3, 2), fen=rnbqkbnr/ppp2ppp/3pp3/8/4P3/3P4/PPP2PPP/RNBQKBNR b KQkq - 0 6, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=f2ef9b98-7d0d-4f71-a196-a15f7188ddd6, parentId=e8af5d1a-8b3a-4842-9a67-67a6b1a50bd6, childrenIds=[5a16aa60-8b07-4e4f-8343-f5e76a76f610, 8f297062-96cb-4ad4-b3f8-c29d04eaad8f], san=Nc3, from=(1, 7), to=(2, 5), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/2N5/PPPP1PPP/R1BQKBNR w KQkq - 1 6, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=5a16aa60-8b07-4e4f-8343-f5e76a76f610, parentId=f2ef9b98-7d0d-4f71-a196-a15f7188ddd6, childrenIds=[], san=a6, from=(0, 1), to=(0, 2), fen=rnbqkbnr/1ppp1ppp/p3p3/8/4P3/2N5/PPPP1PPP/R1BQKBNR b KQkq - 0 7, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=8f297062-96cb-4ad4-b3f8-c29d04eaad8f, parentId=f2ef9b98-7d0d-4f71-a196-a15f7188ddd6, childrenIds=[], san=b6, from=(1, 1), to=(1, 2), fen=rnbqkbnr/p1pp1ppp/1p2p3/8/4P3/2N5/PPPP1PPP/R1BQKBNR b KQkq - 0 7, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=8b0f2d12-c47d-4b65-9f73-0023dd066437, parentId=e5d1ce81-cef5-4ad7-914f-3294b57ad090, childrenIds=[], san=a6, from=(0, 1), to=(0, 2), fen=rnbqkbnr/1ppppppp/p7/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 8, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=55e3d034-d278-4cbe-b574-790f29896283, parentId=e5d1ce81-cef5-4ad7-914f-3294b57ad090, childrenIds=[19614e90-df9f-4a33-b4b2-e28198da538a], san=d6, from=(3, 1), to=(3, 2), fen=rnbqkbnr/ppp1pppp/3p4/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 8, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=19614e90-df9f-4a33-b4b2-e28198da538a, parentId=55e3d034-d278-4cbe-b574-790f29896283, childrenIds=[], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/ppp1pppp/3p4/8/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 9, isActualMove=true, isWhiteMove=true)\n"


        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba1).toMutableList())
        val expected = "1. e4 e5 (1... e6 2. d4 (2. d3 d6) (2. Nc3 a6 (2... b6)) 2... d5) (1... a6) (1... d6 2. d4) 2. Nf3 Nc6 3. Bb5"
        assertEquals(expected, result)
    }


    @Test
    fun testMovesNodesToChessNotation2(){
        val prueba2 = "MoveNode(id=root, parentId=null, childrenIds=[34233801-4a14-40b2-b7c6-73dd4b23199e], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=34233801-4a14-40b2-b7c6-73dd4b23199e, parentId=root, childrenIds=[bab2297f-28f0-4539-8ec8-4708b9bdedd3, c3f05a20-7dd7-4e99-907e-a519558daf55, c120c893-7b9e-4e36-b685-2d9dc2198c47], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=bab2297f-28f0-4539-8ec8-4708b9bdedd3, parentId=34233801-4a14-40b2-b7c6-73dd4b23199e, childrenIds=[4c9b8a62-08fa-4e93-82df-eda51427d291], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=4c9b8a62-08fa-4e93-82df-eda51427d291, parentId=bab2297f-28f0-4539-8ec8-4708b9bdedd3, childrenIds=[], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=c3f05a20-7dd7-4e99-907e-a519558daf55, parentId=34233801-4a14-40b2-b7c6-73dd4b23199e, childrenIds=[c2d16a0f-85db-410d-b81e-284ad134ec58], san=e6, from=(4, 1), to=(4, 2), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=c2d16a0f-85db-410d-b81e-284ad134ec58, parentId=c3f05a20-7dd7-4e99-907e-a519558daf55, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 1 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=c120c893-7b9e-4e36-b685-2d9dc2198c47, parentId=34233801-4a14-40b2-b7c6-73dd4b23199e, childrenIds=[c4115e18-eeab-42db-9d42-0d8b5072fad9, a08b7a15-42df-43b7-bb18-4a4112f5d1dc], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=c4115e18-eeab-42db-9d42-0d8b5072fad9, parentId=c120c893-7b9e-4e36-b685-2d9dc2198c47, childrenIds=[], san=Nc3, from=(1, 7), to=(2, 5), fen=rnbqkbnr/ppp1pppp/8/3p4/4P3/2N5/PPPP1PPP/R1BQKBNR w KQkq - 1 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=a08b7a15-42df-43b7-bb18-4a4112f5d1dc, parentId=c120c893-7b9e-4e36-b685-2d9dc2198c47, childrenIds=[], san=a4, from=(0, 6), to=(0, 4), fen=rnbqkbnr/ppp1pppp/8/3p4/P3P3/8/1PPP1PPP/RNBQKBNR w KQkq - 0 5, isActualMove=true, isWhiteMove=true)"


        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba2).toMutableList())
        val expected = "1. e4 e5 (1... e6 2. Nf3) (1... d5 2. Nc3 (2. a4)) 2. d4"
        assertEquals(expected, result)
    }



    @Test
    fun testMovesNodesToChessNotation3(){
        val prueba3 = "MoveNode(id=root, parentId=null, childrenIds=[13d1e416-e04a-4a90-adb7-956a9b3acf45], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=13d1e416-e04a-4a90-adb7-956a9b3acf45, parentId=root, childrenIds=[598ad562-6a47-4476-8efa-2010fa029ac1, d43dcad9-65df-4cd8-af92-6bdb11221eec, 8c875a6c-8a49-4b7e-915d-27d642983437], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=598ad562-6a47-4476-8efa-2010fa029ac1, parentId=13d1e416-e04a-4a90-adb7-956a9b3acf45, childrenIds=[303f0ca2-234f-4807-b109-73151636a935, cd339d03-644e-4deb-b10e-4e940e1e9312], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=303f0ca2-234f-4807-b109-73151636a935, parentId=598ad562-6a47-4476-8efa-2010fa029ac1, childrenIds=[b96844fa-7d94-46fe-87d2-d50721c344cf], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 1 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=b96844fa-7d94-46fe-87d2-d50721c344cf, parentId=303f0ca2-234f-4807-b109-73151636a935, childrenIds=[e469e032-a8c0-49cf-b7e9-f88f07116374], san=Nc6, from=(1, 0), to=(2, 2), fen=r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 2 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=e469e032-a8c0-49cf-b7e9-f88f07116374, parentId=b96844fa-7d94-46fe-87d2-d50721c344cf, childrenIds=[], san=Bb5, from=(5, 7), to=(1, 3), fen=r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 3 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=cd339d03-644e-4deb-b10e-4e940e1e9312, parentId=598ad562-6a47-4476-8efa-2010fa029ac1, childrenIds=[], san=d3, from=(3, 6), to=(3, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/3P4/PPP2PPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=d43dcad9-65df-4cd8-af92-6bdb11221eec, parentId=13d1e416-e04a-4a90-adb7-956a9b3acf45, childrenIds=[94c54600-2f2f-4f1b-ae2f-d13868676ae4, 50c6d11e-030e-470b-be4f-29f0e23fd245], san=e6, from=(4, 1), to=(4, 2), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=94c54600-2f2f-4f1b-ae2f-d13868676ae4, parentId=d43dcad9-65df-4cd8-af92-6bdb11221eec, childrenIds=[e794b704-f45c-4c23-9aa9-a0a6c16e83db], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppp1ppp/4p3/8/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=e794b704-f45c-4c23-9aa9-a0a6c16e83db, parentId=94c54600-2f2f-4f1b-ae2f-d13868676ae4, childrenIds=[83039bad-aecd-4f68-9f84-0d425bb8ba47, 29f4060e-8756-4e20-bb81-0a1d97385a0d], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp2ppp/4p3/3p4/3PP3/8/PPP2PPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=83039bad-aecd-4f68-9f84-0d425bb8ba47, parentId=e794b704-f45c-4c23-9aa9-a0a6c16e83db, childrenIds=[a19988e3-5daf-47c6-90e5-98b90566cb28], san=e5, from=(4, 4), to=(4, 3), fen=rnbqkbnr/ppp2ppp/4p3/3pP3/3P4/8/PPP2PPP/RNBQKBNR w KQkq - 0 6, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=a19988e3-5daf-47c6-90e5-98b90566cb28, parentId=83039bad-aecd-4f68-9f84-0d425bb8ba47, childrenIds=[], san=c5, from=(2, 1), to=(2, 3), fen=rnbqkbnr/pp3ppp/4p3/2ppP3/3P4/8/PPP2PPP/RNBQKBNR b KQkq - 0 6, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=29f4060e-8756-4e20-bb81-0a1d97385a0d, parentId=e794b704-f45c-4c23-9aa9-a0a6c16e83db, childrenIds=[1db17ee3-f159-405f-b9c6-0e81980c05c8, 1353bfeb-e888-4a73-8631-8901864cc1ab], san=f4, from=(5, 6), to=(5, 4), fen=rnbqkbnr/ppp2ppp/4p3/3p4/3PPP2/8/PPP3PP/RNBQKBNR w KQkq - 0 7, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=1db17ee3-f159-405f-b9c6-0e81980c05c8, parentId=29f4060e-8756-4e20-bb81-0a1d97385a0d, childrenIds=[4164cde0-4cbe-47c6-a8c4-fa38c19a7e80], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppp3pp/4p3/3p1p2/3PPP2/8/PPP3PP/RNBQKBNR b KQkq - 0 7, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=4164cde0-4cbe-47c6-a8c4-fa38c19a7e80, parentId=1db17ee3-f159-405f-b9c6-0e81980c05c8, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/ppp3pp/4p3/3p1p2/3PPP2/5N2/PPP3PP/RNBQKB1R w KQkq - 1 8, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=1353bfeb-e888-4a73-8631-8901864cc1ab, parentId=29f4060e-8756-4e20-bb81-0a1d97385a0d, childrenIds=[45c44a73-ae91-450f-be75-9897b16a4bb8], san=f6, from=(5, 1), to=(5, 2), fen=rnbqkbnr/ppp3pp/4pp2/3p4/3PPP2/8/PPP3PP/RNBQKBNR b KQkq - 0 8, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=45c44a73-ae91-450f-be75-9897b16a4bb8, parentId=1353bfeb-e888-4a73-8631-8901864cc1ab, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/ppp3pp/4pp2/3p4/3PPP2/5N2/PPP3PP/RNBQKB1R w KQkq - 1 9, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=50c6d11e-030e-470b-be4f-29f0e23fd245, parentId=d43dcad9-65df-4cd8-af92-6bdb11221eec, childrenIds=[260f475c-201e-4199-8b4e-131019a07aae], san=d3, from=(3, 6), to=(3, 5), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/3P4/PPP2PPP/RNBQKBNR w KQkq - 0 9, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=260f475c-201e-4199-8b4e-131019a07aae, parentId=50c6d11e-030e-470b-be4f-29f0e23fd245, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp2ppp/4p3/3p4/4P3/3P4/PPP2PPP/RNBQKBNR b KQkq - 0 10, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=8c875a6c-8a49-4b7e-915d-27d642983437, parentId=13d1e416-e04a-4a90-adb7-956a9b3acf45, childrenIds=[], san=a6, from=(0, 1), to=(0, 2), fen=rnbqkbnr/1ppppppp/p7/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 10, isActualMove=true, isWhiteMove=false)"

        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba3).toMutableList())
        val expected = "1. e4 e5 (1... e6 2. d4 (2. d3 d5) 2... d5 3. e5 (3. f4 f5 (3... f6 4. Nf3) 4. Nf3) 3... c5) (1... a6) 2. Nf3 (2. d3) 2... Nc6 3. Bb5"
        assertEquals(expected, result)
    }







    private fun parseMoveNodes(text: String): List<MoveNode> {
        val lineRegex = Regex("""^MoveNode\(id=(.*?),\s*parentId=(.*?),\s*childrenIds=\[(.*?)\],\s*san=(.*?),\s*from=(.*?),\s*to=(.*?),\s*fen=(.*?),\s*isActualMove=(.*?),\s*isWhiteMove=(.*?)\)$""")
        fun parsePair(s: String): Pair<Int, Int>? {
            val t = s.trim()
            if (t == "null") return null
            val m = Regex("""\(([-]?\d+)\s*,\s*([-]?\d+)\)""").find(t) ?: return null
            return Pair(m.groupValues[1].toInt(), m.groupValues[2].toInt())
        }
        return text.lineSequence()
            .map { it.trim() }
            .filter { it.startsWith("MoveNode(") }
            .mapNotNull { line ->
                val m = lineRegex.find(line) ?: return@mapNotNull null
                val id = m.groupValues[1].trim()
                val parentId = m.groupValues[2].trim().let { if (it == "null") null else it }
                val children = m.groupValues[3].trim().let { if (it.isEmpty()) emptyList() else it.split(Regex("\\s*,\\s*")) }.toMutableList()
                val san = m.groupValues[4].trim().let { if (it == "null") null else it }
                val from = parsePair(m.groupValues[5])
                val to = parsePair(m.groupValues[6])
                val fen = m.groupValues[7].trim()
                val isActualMove = m.groupValues[8].trim().toBooleanStrictOrNull() ?: false
                val isWhiteMove = m.groupValues[9].trim().let { if (it == "null") null else it.toBooleanStrict() }
                MoveNode(id = id, parentId = parentId, childrenIds = children, san = san, from = from, to = to, fen = fen, isActualMove = isActualMove, isWhiteMove = isWhiteMove)
            }.toList()
    }
}