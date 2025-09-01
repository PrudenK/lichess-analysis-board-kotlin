import org.junit.Test
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.nodes.PGNHandler
import kotlin.test.assertEquals

class PGNHandlerTest {



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


    @Test
    fun testMovesNodesToChessNotation4(){
        val prueba4 = "MoveNode(id=root, parentId=null, childrenIds=[caf7821c-bb1c-4cf8-b94d-a7145130aedb, 9de35edd-b95d-482f-8ebd-8a441f407918, 4ed83935-a8e6-4fff-a48e-24305722b659], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=caf7821c-bb1c-4cf8-b94d-a7145130aedb, parentId=root, childrenIds=[edbc3295-3590-4bef-bfa7-091a06b3b570], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=edbc3295-3590-4bef-bfa7-091a06b3b570, parentId=caf7821c-bb1c-4cf8-b94d-a7145130aedb, childrenIds=[dfa6d51b-6354-4896-93ad-0b5d4dca1600], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=dfa6d51b-6354-4896-93ad-0b5d4dca1600, parentId=edbc3295-3590-4bef-bfa7-091a06b3b570, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 1 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=9de35edd-b95d-482f-8ebd-8a441f407918, parentId=root, childrenIds=[1d628e67-17e0-4d6f-a680-1e9aa7c9764c], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=1d628e67-17e0-4d6f-a680-1e9aa7c9764c, parentId=9de35edd-b95d-482f-8ebd-8a441f407918, childrenIds=[a25220a9-e6c6-4b94-9013-6d04493bc675], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=a25220a9-e6c6-4b94-9013-6d04493bc675, parentId=1d628e67-17e0-4d6f-a680-1e9aa7c9764c, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/5N2/PPP1PPPP/RNBQKB1R w KQkq - 1 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=4ed83935-a8e6-4fff-a48e-24305722b659, parentId=root, childrenIds=[13979a47-831c-4747-8656-5e7230d2c822], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=13979a47-831c-4747-8656-5e7230d2c822, parentId=4ed83935-a8e6-4fff-a48e-24305722b659, childrenIds=[850deab1-b417-4cab-9b78-23801422e8d8], san=c5, from=(2, 1), to=(2, 3), fen=rnbqkbnr/pp1ppppp/8/2p5/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=850deab1-b417-4cab-9b78-23801422e8d8, parentId=13979a47-831c-4747-8656-5e7230d2c822, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pp1ppppp/8/2p5/2P5/5N2/PP1PPPPP/RNBQKB1R w KQkq - 1 5, isActualMove=true, isWhiteMove=true)"

        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba4).toMutableList())
        val expected = "1. e4 (1. d4 d5 2. Nf3) (1. c4 c5 2. Nf3) 1... e5 2. Nf3"
        assertEquals(expected, result)
    }

    @Test
    fun testMovesNodesToChessNotation5(){
        val prueba5 = "MoveNode(id=root, parentId=null, childrenIds=[b56f7498-7a13-4b83-8275-1c3dcada1cf4], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=b56f7498-7a13-4b83-8275-1c3dcada1cf4, parentId=root, childrenIds=[83357b79-de25-475e-b474-6f4a8db00989], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=83357b79-de25-475e-b474-6f4a8db00989, parentId=b56f7498-7a13-4b83-8275-1c3dcada1cf4, childrenIds=[82c741ef-1f77-4848-aa33-95abfd457420], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=82c741ef-1f77-4848-aa33-95abfd457420, parentId=83357b79-de25-475e-b474-6f4a8db00989, childrenIds=[6b2086ec-513f-4fca-866a-318999252e09], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 1 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=6b2086ec-513f-4fca-866a-318999252e09, parentId=82c741ef-1f77-4848-aa33-95abfd457420, childrenIds=[9f59d82e-1fc6-4e44-8fb2-acf377dae9a0, 07f86fa8-1698-4c82-8933-5e731401c4a6], san=Nc6, from=(1, 0), to=(2, 2), fen=r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 2 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=9f59d82e-1fc6-4e44-8fb2-acf377dae9a0, parentId=6b2086ec-513f-4fca-866a-318999252e09, childrenIds=[], san=Bb5, from=(5, 7), to=(1, 3), fen=r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 3 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=07f86fa8-1698-4c82-8933-5e731401c4a6, parentId=6b2086ec-513f-4fca-866a-318999252e09, childrenIds=[4b616e26-517b-4f81-8995-bbd1820ea84f], san=Bc4, from=(5, 7), to=(2, 4), fen=r1bqkbnr/pppp1ppp/2n5/4p3/2B1P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 3 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=4b616e26-517b-4f81-8995-bbd1820ea84f, parentId=07f86fa8-1698-4c82-8933-5e731401c4a6, childrenIds=[59b04815-36bd-4042-ba57-9e72dffeb5a4, fae2121a-4d10-4d94-8f53-3141b47bbfa2], san=Nf6, from=(6, 0), to=(5, 2), fen=r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 4 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=59b04815-36bd-4042-ba57-9e72dffeb5a4, parentId=4b616e26-517b-4f81-8995-bbd1820ea84f, childrenIds=[], san=d3, from=(3, 6), to=(3, 5), fen=r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=fae2121a-4d10-4d94-8f53-3141b47bbfa2, parentId=4b616e26-517b-4f81-8995-bbd1820ea84f, childrenIds=[680dc4fc-f77d-463b-abfd-dee7d589d142, 74dc4e05-fcfd-4541-aee4-afea7cce9cc4], san=O-O, from=(4, 7), to=(6, 7), fen=r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/5N2/PPPP1PPP/RNBQ2KR w KQkq - 5 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=680dc4fc-f77d-463b-abfd-dee7d589d142, parentId=fae2121a-4d10-4d94-8f53-3141b47bbfa2, childrenIds=[], san=Nxe4, from=(5, 2), to=(4, 4), fen=r1bqkb1r/pppp1ppp/2n5/4p3/2B1n3/5N2/PPPP1PPP/RNBQ1RK1 b kq - 0 6, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=74dc4e05-fcfd-4541-aee4-afea7cce9cc4, parentId=fae2121a-4d10-4d94-8f53-3141b47bbfa2, childrenIds=[], san=g5, from=(6, 1), to=(6, 3), fen=r1bqkb1r/pppp1p1p/2n2n2/4p1p1/2B1P3/5N2/PPPP1PPP/RNBQ2KR b KQkq - 0 6, isActualMove=true, isWhiteMove=false)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba5).toMutableList())
        val expected = "1. e4 e5 2. Nf3 Nc6 3. Bb5 (3. Bc4 Nf6 4. d3 (4. O-O Nxe4 (4... g5)))"
        assertEquals(expected, result)
    }

    @Test
    fun testMovesNodesToChessNotation6(){
        val prueba5 = "MoveNode(id=root, parentId=null, childrenIds=[ef7df266-ecbe-42b7-80f4-da8cef3cea3d], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=ef7df266-ecbe-42b7-80f4-da8cef3cea3d, parentId=root, childrenIds=[cd6a0fdf-bf45-408e-9124-a42fa6f4c9fc], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=cd6a0fdf-bf45-408e-9124-a42fa6f4c9fc, parentId=ef7df266-ecbe-42b7-80f4-da8cef3cea3d, childrenIds=[90a29f7a-fe38-43db-a05d-b23acd716208, 5a91a58d-f3a8-43b6-a71c-66c382c54e75], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=90a29f7a-fe38-43db-a05d-b23acd716208, parentId=cd6a0fdf-bf45-408e-9124-a42fa6f4c9fc, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 1 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=5a91a58d-f3a8-43b6-a71c-66c382c54e75, parentId=cd6a0fdf-bf45-408e-9124-a42fa6f4c9fc, childrenIds=[e1e8f09b-a9cd-4044-a70f-b83373ec626b, af804581-ed93-4aed-a8e5-7c3444a2e582], san=Bb5, from=(5, 7), to=(1, 3), fen=rnbqkbnr/pppp1ppp/8/1B2p3/4P3/8/PPPP1PPP/RNBQK1NR w KQkq - 1 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=e1e8f09b-a9cd-4044-a70f-b83373ec626b, parentId=5a91a58d-f3a8-43b6-a71c-66c382c54e75, childrenIds=[], san=c6, from=(2, 1), to=(2, 2), fen=rnbqkbnr/pp1p1ppp/2p5/1B2p3/4P3/8/PPPP1PPP/RNBQK1NR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=af804581-ed93-4aed-a8e5-7c3444a2e582, parentId=5a91a58d-f3a8-43b6-a71c-66c382c54e75, childrenIds=[], san=b6, from=(1, 1), to=(1, 2), fen=rnbqkbnr/p1pp1ppp/1p6/1B2p3/4P3/8/PPPP1PPP/RNBQK1NR b KQkq - 0 5, isActualMove=true, isWhiteMove=false)"

        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba5).toMutableList())
        val expected = "1. e4 e5 2. Nf3 (2. Bb5 c6 (2... b6))"
        assertEquals(expected, result)
    }

    @Test
    fun testMovesNodesToChessNotation7(){
            val prueba = "MoveNode(id=root, parentId=null, childrenIds=[8fb8fda9-cb70-4efe-9415-93eb1379dc50, e20a9a91-452a-43f9-b5e6-c05d4c131fd4, 920cf307-baac-4e2c-8ad5-2f21ea6880d3, 56ffaec0-62c9-4fd4-a07f-3cf14f757d50, 8c164013-d409-4596-b6ab-6255bd896fec], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                    "MoveNode(id=8fb8fda9-cb70-4efe-9415-93eb1379dc50, parentId=root, childrenIds=[8a45c1af-e67f-49f4-ae76-8bd867b44e30], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=8a45c1af-e67f-49f4-ae76-8bd867b44e30, parentId=8fb8fda9-cb70-4efe-9415-93eb1379dc50, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=e20a9a91-452a-43f9-b5e6-c05d4c131fd4, parentId=root, childrenIds=[c3b3fb8e-2ef7-4081-90a8-e3a77b656aad], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=c3b3fb8e-2ef7-4081-90a8-e3a77b656aad, parentId=e20a9a91-452a-43f9-b5e6-c05d4c131fd4, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=920cf307-baac-4e2c-8ad5-2f21ea6880d3, parentId=root, childrenIds=[], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=56ffaec0-62c9-4fd4-a07f-3cf14f757d50, parentId=root, childrenIds=[], san=b4, from=(1, 6), to=(1, 4), fen=rnbqkbnr/pppppppp/8/8/1P6/8/P1PPPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=8c164013-d409-4596-b6ab-6255bd896fec, parentId=root, childrenIds=[6aae4ec8-e5e9-49fd-8ece-aeeb49764e18], san=a4, from=(0, 6), to=(0, 4), fen=rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=6aae4ec8-e5e9-49fd-8ece-aeeb49764e18, parentId=8c164013-d409-4596-b6ab-6255bd896fec, childrenIds=[20c1940e-53e3-4ae1-8df6-26b047bad608, dff35da1-6078-4d66-a74c-27a55445ab91], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/P7/8/1PPPPPPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                    "MoveNode(id=20c1940e-53e3-4ae1-8df6-26b047bad608, parentId=6aae4ec8-e5e9-49fd-8ece-aeeb49764e18, childrenIds=[], san=b3, from=(1, 6), to=(1, 5), fen=rnbqkbnr/ppp1pppp/8/3p4/P7/1P6/2PPPPPP/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                    "MoveNode(id=dff35da1-6078-4d66-a74c-27a55445ab91, parentId=6aae4ec8-e5e9-49fd-8ece-aeeb49764e18, childrenIds=[], san=c3, from=(2, 6), to=(2, 5), fen=rnbqkbnr/ppp1pppp/8/3p4/P7/2P5/1P1PPPPP/RNBQKBNR w KQkq - 0 6, isActualMove=true, isWhiteMove=true)"
            val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4 d5) (1. c4) (1. b4) (1. a4 d5 2. b3 (2. c3)) 1... e5"
        assertEquals(expected, result)
    }

    @Test
    fun testMovesNodesToChessNotation8(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[f0ee8ac9-087f-4608-bf36-9a3434aec9c0], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=f0ee8ac9-087f-4608-bf36-9a3434aec9c0, parentId=root, childrenIds=[264222b9-52c1-4ae7-89e1-c6d172c9fa72], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=264222b9-52c1-4ae7-89e1-c6d172c9fa72, parentId=f0ee8ac9-087f-4608-bf36-9a3434aec9c0, childrenIds=[c027427d-cfbc-47b5-bfbe-0eae8a8d58d5, c07de00b-483b-4c2d-92ef-062dc6ec5776, 331b845d-e4df-4e0b-9583-437065b0eda7, dd134e09-0bc5-42d4-8e18-7fccb8a7fe3e], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=c027427d-cfbc-47b5-bfbe-0eae8a8d58d5, parentId=264222b9-52c1-4ae7-89e1-c6d172c9fa72, childrenIds=[17b3c4e9-3cff-455c-a2af-b278702dc02c], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=17b3c4e9-3cff-455c-a2af-b278702dc02c, parentId=c027427d-cfbc-47b5-bfbe-0eae8a8d58d5, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp2ppp/8/3pp3/3PP3/8/PPP2PPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=c07de00b-483b-4c2d-92ef-062dc6ec5776, parentId=264222b9-52c1-4ae7-89e1-c6d172c9fa72, childrenIds=[9a51cfac-3ad8-4754-9c24-b087c1f52b20], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/2P1P3/8/PP1P1PPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=9a51cfac-3ad8-4754-9c24-b087c1f52b20, parentId=c07de00b-483b-4c2d-92ef-062dc6ec5776, childrenIds=[], san=c5, from=(2, 1), to=(2, 3), fen=rnbqkbnr/pp1p1ppp/8/2p1p3/2P1P3/8/PP1P1PPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=331b845d-e4df-4e0b-9583-437065b0eda7, parentId=264222b9-52c1-4ae7-89e1-c6d172c9fa72, childrenIds=[], san=b4, from=(1, 6), to=(1, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/1P2P3/8/P1PP1PPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=dd134e09-0bc5-42d4-8e18-7fccb8a7fe3e, parentId=264222b9-52c1-4ae7-89e1-c6d172c9fa72, childrenIds=[], san=a4, from=(0, 6), to=(0, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/P3P3/8/1PPP1PPP/RNBQKBNR w KQkq - 0 5, isActualMove=true, isWhiteMove=true)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 e5 2. d4 (2. c4 c5) (2. b4) (2. a4) 2... d5"
        assertEquals(expected, result)
    }


    @Test
    fun testMovesNodesToChessNotation9(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[3f483463-9de1-4ef3-b980-b74f8ed63257, b11f13f9-bc54-4f1f-9f93-4c19be5ba0f1, 00ac9f5d-dfc0-4ced-9ae8-b36de6dc6977, 75fc6c45-e28f-4e13-be35-b354f2b4fc5a, 01490515-f9b6-4f7a-a218-1a4ba145f7d8], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=3f483463-9de1-4ef3-b980-b74f8ed63257, parentId=root, childrenIds=[6810650f-d608-4ff0-8b57-aedb22b69e3c, 9267216b-2de2-48a3-a1a1-be51a693fdd7], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=6810650f-d608-4ff0-8b57-aedb22b69e3c, parentId=3f483463-9de1-4ef3-b980-b74f8ed63257, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=b11f13f9-bc54-4f1f-9f93-4c19be5ba0f1, parentId=root, childrenIds=[a33c4109-83b7-47d5-ad10-46230d99bf9a], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=a33c4109-83b7-47d5-ad10-46230d99bf9a, parentId=b11f13f9-bc54-4f1f-9f93-4c19be5ba0f1, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=00ac9f5d-dfc0-4ced-9ae8-b36de6dc6977, parentId=root, childrenIds=[], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=75fc6c45-e28f-4e13-be35-b354f2b4fc5a, parentId=root, childrenIds=[], san=b4, from=(1, 6), to=(1, 4), fen=rnbqkbnr/pppppppp/8/8/1P6/8/P1PPPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=01490515-f9b6-4f7a-a218-1a4ba145f7d8, parentId=root, childrenIds=[], san=a4, from=(0, 6), to=(0, 4), fen=rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=9267216b-2de2-48a3-a1a1-be51a693fdd7, parentId=3f483463-9de1-4ef3-b980-b74f8ed63257, childrenIds=[], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppppp1pp/8/5p2/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 6, isActualMove=true, isWhiteMove=false)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4 d5) (1. c4) (1. b4) (1. a4) 1... e5 (1... f5)"
        assertEquals(expected, result)
    }

    @Test
    fun testMovesNodesToChessNotation10(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[0d7412f7-b043-4f36-97a5-ce64be197438, 0baa5992-1b5f-4395-ba2c-04e8b17e9dfa, 0ff277c0-39d3-40ba-aab1-7d2e61320304, e5270247-8626-452b-8a33-5b329e35f00f, bcd6da73-3710-4f9d-b1e3-50896a262d83], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=0d7412f7-b043-4f36-97a5-ce64be197438, parentId=root, childrenIds=[0fa75fac-0cae-4580-bd02-c35cee7df86b], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=0fa75fac-0cae-4580-bd02-c35cee7df86b, parentId=0d7412f7-b043-4f36-97a5-ce64be197438, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=0baa5992-1b5f-4395-ba2c-04e8b17e9dfa, parentId=root, childrenIds=[], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=0ff277c0-39d3-40ba-aab1-7d2e61320304, parentId=root, childrenIds=[], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=e5270247-8626-452b-8a33-5b329e35f00f, parentId=root, childrenIds=[], san=b4, from=(1, 6), to=(1, 4), fen=rnbqkbnr/pppppppp/8/8/1P6/8/P1PPPPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=bcd6da73-3710-4f9d-b1e3-50896a262d83, parentId=root, childrenIds=[ef57f4d8-555d-438b-8cbb-0f117f1f48bf, 2b57710a-a0df-4ce1-9f53-03f48f9a6019], san=a4, from=(0, 6), to=(0, 4), fen=rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=ef57f4d8-555d-438b-8cbb-0f117f1f48bf, parentId=bcd6da73-3710-4f9d-b1e3-50896a262d83, childrenIds=[], san=c5, from=(2, 1), to=(2, 3), fen=rnbqkbnr/pp1ppppp/8/2p5/P7/8/1PPPPPPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=2b57710a-a0df-4ce1-9f53-03f48f9a6019, parentId=bcd6da73-3710-4f9d-b1e3-50896a262d83, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/P7/8/1PPPPPPP/RNBQKBNR b KQkq - 0 5, isActualMove=true, isWhiteMove=false)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4) (1. c4) (1. b4) (1. a4 c5 (1... d5)) 1... e5"
        assertEquals(expected, result)
    }

    @Test
    fun testMovesNodesToChessNotation11(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[547f35ac-b4bc-4a6a-bc54-3a6b34958227, 6325495a-26db-4987-bbd3-dde963937738, 77311a49-9c3b-4601-9bcf-3ad5dda99e19], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=547f35ac-b4bc-4a6a-bc54-3a6b34958227, parentId=root, childrenIds=[727b0a08-93ec-4dd3-83fe-1e40c2d7a4f2], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=727b0a08-93ec-4dd3-83fe-1e40c2d7a4f2, parentId=547f35ac-b4bc-4a6a-bc54-3a6b34958227, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=6325495a-26db-4987-bbd3-dde963937738, parentId=root, childrenIds=[], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=77311a49-9c3b-4601-9bcf-3ad5dda99e19, parentId=root, childrenIds=[d5edc99c-2dcb-4cb9-8c37-7bba6da8ed60, 18aae6d7-058b-4b5a-87a2-33a8ecbc99a5, 963c5189-e993-4888-93b7-af73af270fc4, 8903aac4-4de0-4256-aa0e-ecaec214d247, 9497bc39-dc2d-447c-8f50-e6a3eddc0ace], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 3, isActualMove=true, isWhiteMove=true)\n" +
                "MoveNode(id=d5edc99c-2dcb-4cb9-8c37-7bba6da8ed60, parentId=77311a49-9c3b-4601-9bcf-3ad5dda99e19, childrenIds=[1c410f62-fe7e-49d9-9079-4f8f7133d298], san=c5, from=(2, 1), to=(2, 3), fen=rnbqkbnr/pp1ppppp/8/2p5/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=1c410f62-fe7e-49d9-9079-4f8f7133d298, parentId=d5edc99c-2dcb-4cb9-8c37-7bba6da8ed60, childrenIds=[], san=b3, from=(1, 6), to=(1, 5), fen=rnbqkbnr/pp1ppppp/8/2p5/2P5/1P6/P2PPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=18aae6d7-058b-4b5a-87a2-33a8ecbc99a5, parentId=77311a49-9c3b-4601-9bcf-3ad5dda99e19, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=963c5189-e993-4888-93b7-af73af270fc4, parentId=77311a49-9c3b-4601-9bcf-3ad5dda99e19, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=8903aac4-4de0-4256-aa0e-ecaec214d247, parentId=77311a49-9c3b-4601-9bcf-3ad5dda99e19, childrenIds=[], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppppp1pp/8/5p2/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=9497bc39-dc2d-447c-8f50-e6a3eddc0ace, parentId=77311a49-9c3b-4601-9bcf-3ad5dda99e19, childrenIds=[], san=g5, from=(6, 1), to=(6, 3), fen=rnbqkbnr/pppppp1p/8/6p1/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 6, isActualMove=false, isWhiteMove=false)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4) (1. c4 c5 (1... d5) (1... e5) (1... f5) (1... g5) 2. b3) 1... e5"
        assertEquals(expected, result)
    }



    @Test
    fun testMovesNodesToChessNotation12(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[a008c737-f98b-437c-b615-85b526b6fac4, 0793aaa9-00a0-4bbf-a843-ccfe6a30d568, c7970a3e-f36c-451f-a24b-c517b174ba10], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=a008c737-f98b-437c-b615-85b526b6fac4, parentId=root, childrenIds=[], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=0793aaa9-00a0-4bbf-a843-ccfe6a30d568, parentId=root, childrenIds=[], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=c7970a3e-f36c-451f-a24b-c517b174ba10, parentId=root, childrenIds=[77fa600e-c234-4683-914f-0f24705b8267, 42ae9833-d905-4cc0-8456-158ae8af6964, a66a29bd-596b-4edb-8e34-fa33559a64f9], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=77fa600e-c234-4683-914f-0f24705b8267, parentId=c7970a3e-f36c-451f-a24b-c517b174ba10, childrenIds=[], san=c5, from=(2, 1), to=(2, 3), fen=rnbqkbnr/pp1ppppp/8/2p5/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=42ae9833-d905-4cc0-8456-158ae8af6964, parentId=c7970a3e-f36c-451f-a24b-c517b174ba10, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=a66a29bd-596b-4edb-8e34-fa33559a64f9, parentId=c7970a3e-f36c-451f-a24b-c517b174ba10, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 4, isActualMove=true, isWhiteMove=false)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4) (1. c4 c5 (1... d5) (1... e5))"
        assertEquals(expected, result)
    }

    @Test
    fun testMovesNodesToChessNotation13(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[096efae3-4b4b-4c53-9a57-eb0fda4499ce, ddcf75f8-9b3d-413d-8d9a-b7a4a6d6bf23, ada86346-2ade-4335-91c0-45a617e87b60, 859d25e5-dc13-4f77-ab6b-1c7f06e1482e], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=096efae3-4b4b-4c53-9a57-eb0fda4499ce, parentId=root, childrenIds=[eb66f297-8a00-4af3-b0e5-d0877387286a, 790db122-df62-4de6-8228-670176372879], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=eb66f297-8a00-4af3-b0e5-d0877387286a, parentId=096efae3-4b4b-4c53-9a57-eb0fda4499ce, childrenIds=[8d50ae77-06b2-48e3-8a67-249c02a66d76], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=790db122-df62-4de6-8228-670176372879, parentId=096efae3-4b4b-4c53-9a57-eb0fda4499ce, childrenIds=[], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppppp1pp/8/5p2/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=ddcf75f8-9b3d-413d-8d9a-b7a4a6d6bf23, parentId=root, childrenIds=[b727a464-ff58-48c7-91cc-eec71255961f], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=b727a464-ff58-48c7-91cc-eec71255961f, parentId=ddcf75f8-9b3d-413d-8d9a-b7a4a6d6bf23, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=ada86346-2ade-4335-91c0-45a617e87b60, parentId=root, childrenIds=[], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=859d25e5-dc13-4f77-ab6b-1c7f06e1482e, parentId=root, childrenIds=[fe40a004-fa37-426a-818b-6e9e432055c6, 0f01ae9c-4f7d-410d-b803-f37bdfab5ff6], san=b4, from=(1, 6), to=(1, 4), fen=rnbqkbnr/pppppppp/8/8/1P6/8/P1PPPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=fe40a004-fa37-426a-818b-6e9e432055c6, parentId=859d25e5-dc13-4f77-ab6b-1c7f06e1482e, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/1P6/8/P1PPPPPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=0f01ae9c-4f7d-410d-b803-f37bdfab5ff6, parentId=859d25e5-dc13-4f77-ab6b-1c7f06e1482e, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/1P6/8/P1PPPPPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=8d50ae77-06b2-48e3-8a67-249c02a66d76, parentId=eb66f297-8a00-4af3-b0e5-d0877387286a, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 1 7, isActualMove=true, isWhiteMove=true)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4 d5) (1. c4) (1. b4 e5 (1... d5)) 1... e5 (1... f5) 2. Nf3"
        assertEquals(expected, result)
    }


    @Test
    fun testMovesNodesToChessNotation14(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[477121e9-da07-4769-ad4b-fce8fbe12dfd, 55c042c0-727f-4342-8653-4d3a7a6f06df, e2f56d7d-009a-4786-bdca-2dfd81f61a8f], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=477121e9-da07-4769-ad4b-fce8fbe12dfd, parentId=root, childrenIds=[cfae8656-3062-447e-b3d9-96e3c807610a, c7624ec7-fe4d-4bde-8d62-23acceaeda57], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=cfae8656-3062-447e-b3d9-96e3c807610a, parentId=477121e9-da07-4769-ad4b-fce8fbe12dfd, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=c7624ec7-fe4d-4bde-8d62-23acceaeda57, parentId=477121e9-da07-4769-ad4b-fce8fbe12dfd, childrenIds=[], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppppp1pp/8/5p2/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=55c042c0-727f-4342-8653-4d3a7a6f06df, parentId=root, childrenIds=[7cac0bfd-6f54-44cb-9183-279c76961b57, 854f06e1-99d6-4d2d-b2a6-a667271321d9], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=7cac0bfd-6f54-44cb-9183-279c76961b57, parentId=55c042c0-727f-4342-8653-4d3a7a6f06df, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=854f06e1-99d6-4d2d-b2a6-a667271321d9, parentId=55c042c0-727f-4342-8653-4d3a7a6f06df, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=e2f56d7d-009a-4786-bdca-2dfd81f61a8f, parentId=root, childrenIds=[], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 4, isActualMove=true, isWhiteMove=true)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4 d5 (1... e5)) (1. c4) 1... e5 (1... f5)"
        assertEquals(expected, result)
    }

    @Test
    fun testMovesNodesToChessNotation15(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[9f86526b-a5a5-40df-a94d-926a368890a0, 46066a36-ec22-4a3c-9a6a-e0723a1a8f3b, b38f0467-9e3a-48b9-88f6-3e766794541f], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=9f86526b-a5a5-40df-a94d-926a368890a0, parentId=root, childrenIds=[1ad0b5ce-6a49-4ab8-9d7d-2c64047cca6d, 7ac2f75e-c925-4408-90dc-da75b2fe4b8e], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=1ad0b5ce-6a49-4ab8-9d7d-2c64047cca6d, parentId=9f86526b-a5a5-40df-a94d-926a368890a0, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=7ac2f75e-c925-4408-90dc-da75b2fe4b8e, parentId=9f86526b-a5a5-40df-a94d-926a368890a0, childrenIds=[], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppppp1pp/8/5p2/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=46066a36-ec22-4a3c-9a6a-e0723a1a8f3b, parentId=root, childrenIds=[266bf0d5-e5db-4ec1-9700-47fa1ce47662, 4c2468d9-75bc-4e92-88a0-e107f325edf1], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=266bf0d5-e5db-4ec1-9700-47fa1ce47662, parentId=46066a36-ec22-4a3c-9a6a-e0723a1a8f3b, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=4c2468d9-75bc-4e92-88a0-e107f325edf1, parentId=46066a36-ec22-4a3c-9a6a-e0723a1a8f3b, childrenIds=[003d0306-0aa4-4440-addb-6eaac7e0d6fc, 940765f5-43f6-4a2b-b55c-1d13bd91f1c0], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=003d0306-0aa4-4440-addb-6eaac7e0d6fc, parentId=4c2468d9-75bc-4e92-88a0-e107f325edf1, childrenIds=[], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=940765f5-43f6-4a2b-b55c-1d13bd91f1c0, parentId=4c2468d9-75bc-4e92-88a0-e107f325edf1, childrenIds=[], san=f3, from=(5, 6), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/3P4/5P2/PPP1P1PP/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=b38f0467-9e3a-48b9-88f6-3e766794541f, parentId=root, childrenIds=[], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 5, isActualMove=true, isWhiteMove=true)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4 d5 (1... e5 2. e4 (2. f3))) (1. c4) 1... e5 (1... f5)"
        assertEquals(expected, result)
    }


    @Test
    fun testMovesNodesToChessNotation16(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[1e9940ba-345c-42f9-bce0-26a872d280f4, 9506ac34-e265-40c7-9f24-7d3c78faf7ba], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=1e9940ba-345c-42f9-bce0-26a872d280f4, parentId=root, childrenIds=[e33f1fd0-c5c0-46e4-8bbd-f0c29ebbca45, 6657c0d0-3866-4ee6-be99-cdf868fd59d0], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=e33f1fd0-c5c0-46e4-8bbd-f0c29ebbca45, parentId=1e9940ba-345c-42f9-bce0-26a872d280f4, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=6657c0d0-3866-4ee6-be99-cdf868fd59d0, parentId=1e9940ba-345c-42f9-bce0-26a872d280f4, childrenIds=[], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppppp1pp/8/5p2/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=9506ac34-e265-40c7-9f24-7d3c78faf7ba, parentId=root, childrenIds=[598cb69b-9c70-4875-883a-20f54d8f3fb9, fa1595a3-9e3b-4057-a19f-974a9b14fd06], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=598cb69b-9c70-4875-883a-20f54d8f3fb9, parentId=9506ac34-e265-40c7-9f24-7d3c78faf7ba, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=fa1595a3-9e3b-4057-a19f-974a9b14fd06, parentId=9506ac34-e265-40c7-9f24-7d3c78faf7ba, childrenIds=[31c0c405-d587-4189-bcb6-9e90501aecb6, b27cc5e7-4a13-45d7-b2a0-e5a4aee7515f], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=31c0c405-d587-4189-bcb6-9e90501aecb6, parentId=fa1595a3-9e3b-4057-a19f-974a9b14fd06, childrenIds=[], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=b27cc5e7-4a13-45d7-b2a0-e5a4aee7515f, parentId=fa1595a3-9e3b-4057-a19f-974a9b14fd06, childrenIds=[], san=f3, from=(5, 6), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/3P4/5P2/PPP1P1PP/RNBQKBNR w KQkq - 0 5, isActualMove=true, isWhiteMove=true)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4 d5 (1... e5 2. e4 (2. f3))) 1... e5 (1... f5)"
        assertEquals(expected, result)
    }


    @Test
    fun testMovesNodesToChessNotation17(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[1e9940ba-345c-42f9-bce0-26a872d280f4, 9506ac34-e265-40c7-9f24-7d3c78faf7ba], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=1e9940ba-345c-42f9-bce0-26a872d280f4, parentId=root, childrenIds=[e33f1fd0-c5c0-46e4-8bbd-f0c29ebbca45, 6657c0d0-3866-4ee6-be99-cdf868fd59d0], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=e33f1fd0-c5c0-46e4-8bbd-f0c29ebbca45, parentId=1e9940ba-345c-42f9-bce0-26a872d280f4, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=6657c0d0-3866-4ee6-be99-cdf868fd59d0, parentId=1e9940ba-345c-42f9-bce0-26a872d280f4, childrenIds=[], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppppp1pp/8/5p2/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=9506ac34-e265-40c7-9f24-7d3c78faf7ba, parentId=root, childrenIds=[598cb69b-9c70-4875-883a-20f54d8f3fb9, fa1595a3-9e3b-4057-a19f-974a9b14fd06, bffb7fd7-5469-4200-9b1f-29f06e2808ba], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=598cb69b-9c70-4875-883a-20f54d8f3fb9, parentId=9506ac34-e265-40c7-9f24-7d3c78faf7ba, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=fa1595a3-9e3b-4057-a19f-974a9b14fd06, parentId=9506ac34-e265-40c7-9f24-7d3c78faf7ba, childrenIds=[31c0c405-d587-4189-bcb6-9e90501aecb6, b27cc5e7-4a13-45d7-b2a0-e5a4aee7515f], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=31c0c405-d587-4189-bcb6-9e90501aecb6, parentId=fa1595a3-9e3b-4057-a19f-974a9b14fd06, childrenIds=[], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=b27cc5e7-4a13-45d7-b2a0-e5a4aee7515f, parentId=fa1595a3-9e3b-4057-a19f-974a9b14fd06, childrenIds=[], san=f3, from=(5, 6), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/3P4/5P2/PPP1P1PP/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=bffb7fd7-5469-4200-9b1f-29f06e2808ba, parentId=9506ac34-e265-40c7-9f24-7d3c78faf7ba, childrenIds=[], san=e6, from=(4, 1), to=(4, 2), fen=rnbqkbnr/pppp1ppp/4p3/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 5, isActualMove=true, isWhiteMove=false)"

        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4 d5 (1... e5 2. e4 (2. f3)) (1... e6)) 1... e5 (1... f5)"
        assertEquals(expected, result)
    }



    @Test
    fun testMovesNodesToChessNotation18(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[1e9940ba-345c-42f9-bce0-26a872d280f4, 9506ac34-e265-40c7-9f24-7d3c78faf7ba, ecad442a-3752-4499-b694-45ca8606a842], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=1e9940ba-345c-42f9-bce0-26a872d280f4, parentId=root, childrenIds=[e33f1fd0-c5c0-46e4-8bbd-f0c29ebbca45, 6657c0d0-3866-4ee6-be99-cdf868fd59d0], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=e33f1fd0-c5c0-46e4-8bbd-f0c29ebbca45, parentId=1e9940ba-345c-42f9-bce0-26a872d280f4, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=6657c0d0-3866-4ee6-be99-cdf868fd59d0, parentId=1e9940ba-345c-42f9-bce0-26a872d280f4, childrenIds=[], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppppp1pp/8/5p2/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=9506ac34-e265-40c7-9f24-7d3c78faf7ba, parentId=root, childrenIds=[598cb69b-9c70-4875-883a-20f54d8f3fb9, fa1595a3-9e3b-4057-a19f-974a9b14fd06, bffb7fd7-5469-4200-9b1f-29f06e2808ba], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=598cb69b-9c70-4875-883a-20f54d8f3fb9, parentId=9506ac34-e265-40c7-9f24-7d3c78faf7ba, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=fa1595a3-9e3b-4057-a19f-974a9b14fd06, parentId=9506ac34-e265-40c7-9f24-7d3c78faf7ba, childrenIds=[31c0c405-d587-4189-bcb6-9e90501aecb6, b27cc5e7-4a13-45d7-b2a0-e5a4aee7515f], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=31c0c405-d587-4189-bcb6-9e90501aecb6, parentId=fa1595a3-9e3b-4057-a19f-974a9b14fd06, childrenIds=[], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=b27cc5e7-4a13-45d7-b2a0-e5a4aee7515f, parentId=fa1595a3-9e3b-4057-a19f-974a9b14fd06, childrenIds=[], san=f3, from=(5, 6), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/3P4/5P2/PPP1P1PP/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=bffb7fd7-5469-4200-9b1f-29f06e2808ba, parentId=9506ac34-e265-40c7-9f24-7d3c78faf7ba, childrenIds=[], san=e6, from=(4, 1), to=(4, 2), fen=rnbqkbnr/pppp1ppp/4p3/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=ecad442a-3752-4499-b694-45ca8606a842, parentId=root, childrenIds=[], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 6, isActualMove=true, isWhiteMove=true)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4 d5 (1... e5 2. e4 (2. f3)) (1... e6)) (1. c4) 1... e5 (1... f5)"
        assertEquals(expected, result)
    }


    @Test
    fun testMovesNodesToChessNotation19(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[e09150b4-a477-4332-8151-733c56f23ee5], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=e09150b4-a477-4332-8151-733c56f23ee5, parentId=root, childrenIds=[75265829-9728-469a-a1b7-b7581968aec8, 576a11fb-9701-40bf-ac17-91daca76bac4, 93bc0d98-4593-4c28-9058-76a633af5563], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=75265829-9728-469a-a1b7-b7581968aec8, parentId=e09150b4-a477-4332-8151-733c56f23ee5, childrenIds=[60876298-1699-41c4-8f12-8466166cb125], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=60876298-1699-41c4-8f12-8466166cb125, parentId=75265829-9728-469a-a1b7-b7581968aec8, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 1 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=576a11fb-9701-40bf-ac17-91daca76bac4, parentId=e09150b4-a477-4332-8151-733c56f23ee5, childrenIds=[77b15056-bb38-42b2-bd10-09fecff74cfe, 94ad082c-d0e8-4e3f-9ba1-db7e0b9d5a26, d1e07e3d-e145-4c64-bb86-6b94eea738a8], san=e6, from=(4, 1), to=(4, 2), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=77b15056-bb38-42b2-bd10-09fecff74cfe, parentId=576a11fb-9701-40bf-ac17-91daca76bac4, childrenIds=[64f41158-b411-4210-87ad-56fd0e2979ed], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppp1ppp/4p3/8/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=64f41158-b411-4210-87ad-56fd0e2979ed, parentId=77b15056-bb38-42b2-bd10-09fecff74cfe, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp2ppp/4p3/3p4/3PP3/8/PPP2PPP/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=94ad082c-d0e8-4e3f-9ba1-db7e0b9d5a26, parentId=576a11fb-9701-40bf-ac17-91daca76bac4, childrenIds=[0c2638b6-8f3a-482a-bd09-a1061037dcaa], san=d3, from=(3, 6), to=(3, 5), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/3P4/PPP2PPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=0c2638b6-8f3a-482a-bd09-a1061037dcaa, parentId=94ad082c-d0e8-4e3f-9ba1-db7e0b9d5a26, childrenIds=[], san=d6, from=(3, 1), to=(3, 2), fen=rnbqkbnr/ppp2ppp/3pp3/8/4P3/3P4/PPP2PPP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=d1e07e3d-e145-4c64-bb86-6b94eea738a8, parentId=576a11fb-9701-40bf-ac17-91daca76bac4, childrenIds=[31997f5f-bfb2-4082-9ab5-6030fb13a8e9, 343d7662-37e4-4e0e-a394-1a253236fb93], san=Nc3, from=(1, 7), to=(2, 5), fen=rnbqkbnr/pppp1ppp/4p3/8/4P3/2N5/PPPP1PPP/R1BQKBNR w KQkq - 1 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=31997f5f-bfb2-4082-9ab5-6030fb13a8e9, parentId=d1e07e3d-e145-4c64-bb86-6b94eea738a8, childrenIds=[], san=a6, from=(0, 1), to=(0, 2), fen=rnbqkbnr/1ppp1ppp/p3p3/8/4P3/2N5/PPPP1PPP/R1BQKBNR b KQkq - 0 6, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=343d7662-37e4-4e0e-a394-1a253236fb93, parentId=d1e07e3d-e145-4c64-bb86-6b94eea738a8, childrenIds=[], san=b6, from=(1, 1), to=(1, 2), fen=rnbqkbnr/p1pp1ppp/1p2p3/8/4P3/2N5/PPPP1PPP/R1BQKBNR b KQkq - 0 6, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=93bc0d98-4593-4c28-9058-76a633af5563, parentId=e09150b4-a477-4332-8151-733c56f23ee5, childrenIds=[499748e8-b1de-4fd8-8e31-ab8bed63724a], san=d6, from=(3, 1), to=(3, 2), fen=rnbqkbnr/ppp1pppp/3p4/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 7, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=499748e8-b1de-4fd8-8e31-ab8bed63724a, parentId=93bc0d98-4593-4c28-9058-76a633af5563, childrenIds=[], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/ppp1pppp/3p4/8/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 7, isActualMove=true, isWhiteMove=true)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 e5 (1... e6 2. d4 (2. d3 d6) (2. Nc3 a6 (2... b6)) 2... d5) (1... d6 2. d4) 2. Nf3"
        assertEquals(expected, result)
    }

    @Test
    fun testMovesNodesToChessNotation20(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[7908bb4d-4c65-4c17-9084-58526f3cb63d, eea9457e-1a67-4266-852d-317128d83e55, f94e9e24-8d30-4347-8e4b-ab44e8e72310], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=7908bb4d-4c65-4c17-9084-58526f3cb63d, parentId=root, childrenIds=[b189cb27-e050-4a14-b0c4-f603dfd8f364, c73aae3a-9e44-4b4e-9aa2-16fa99f247ba], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=b189cb27-e050-4a14-b0c4-f603dfd8f364, parentId=7908bb4d-4c65-4c17-9084-58526f3cb63d, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=c73aae3a-9e44-4b4e-9aa2-16fa99f247ba, parentId=7908bb4d-4c65-4c17-9084-58526f3cb63d, childrenIds=[], san=f5, from=(5, 1), to=(5, 3), fen=rnbqkbnr/ppppp1pp/8/5p2/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=eea9457e-1a67-4266-852d-317128d83e55, parentId=root, childrenIds=[ebbc1d27-362a-4827-8576-d647a84954b9], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=ebbc1d27-362a-4827-8576-d647a84954b9, parentId=eea9457e-1a67-4266-852d-317128d83e55, childrenIds=[f92ba2ea-9a25-437d-946d-19e602dc36e1, cda49d45-daba-4014-a8df-450478f2ec12, 2c6fb3a4-29a8-4798-a958-12096c887248], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=f92ba2ea-9a25-437d-946d-19e602dc36e1, parentId=ebbc1d27-362a-4827-8576-d647a84954b9, childrenIds=[], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/ppp1pppp/8/3p4/3PP3/8/PPP2PPP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=cda49d45-daba-4014-a8df-450478f2ec12, parentId=ebbc1d27-362a-4827-8576-d647a84954b9, childrenIds=[], san=f3, from=(5, 6), to=(5, 5), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/5P2/PPP1P1PP/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=2c6fb3a4-29a8-4798-a958-12096c887248, parentId=ebbc1d27-362a-4827-8576-d647a84954b9, childrenIds=[], san=g3, from=(6, 6), to=(6, 5), fen=rnbqkbnr/ppp1pppp/8/3p4/3P4/6P1/PPP1PP1P/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=f94e9e24-8d30-4347-8e4b-ab44e8e72310, parentId=root, childrenIds=[192dfce6-47ee-4e3f-8db4-b262c4d86f2d, 42a54736-b3c9-4560-95d0-3dc983e4f90d], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=192dfce6-47ee-4e3f-8db4-b262c4d86f2d, parentId=f94e9e24-8d30-4347-8e4b-ab44e8e72310, childrenIds=[], san=d5, from=(3, 1), to=(3, 3), fen=rnbqkbnr/ppp1pppp/8/3p4/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 6, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=42a54736-b3c9-4560-95d0-3dc983e4f90d, parentId=f94e9e24-8d30-4347-8e4b-ab44e8e72310, childrenIds=[61e18527-14a3-4f4e-bf6b-32a12f6de80a, a4bcb094-895f-4819-a077-907440ad527a], san=c5, from=(2, 1), to=(2, 3), fen=rnbqkbnr/pp1ppppp/8/2p5/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 6, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=61e18527-14a3-4f4e-bf6b-32a12f6de80a, parentId=42a54736-b3c9-4560-95d0-3dc983e4f90d, childrenIds=[], san=Nf3, from=(6, 7), to=(5, 5), fen=rnbqkbnr/pp1ppppp/8/2p5/2P5/5N2/PP1PPPPP/RNBQKB1R w KQkq - 1 7, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=a4bcb094-895f-4819-a077-907440ad527a, parentId=42a54736-b3c9-4560-95d0-3dc983e4f90d, childrenIds=[], san=Nh3, from=(6, 7), to=(7, 5), fen=rnbqkbnr/pp1ppppp/8/2p5/2P5/7N/PP1PPPPP/RNBQKB1R w KQkq - 1 7, isActualMove=true, isWhiteMove=true)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. d4 d5 2. e4 (2. f3) (2. g3)) (1. c4 d5 (1... c5 2. Nf3 (2. Nh3))) 1... e5 (1... f5)"
        assertEquals(expected, result)
    }


    @Test
    fun testMovesNodesToChessNotation21(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[d03bdbe9-9d87-46bc-9cee-255be56e9c37, 88ce8ff9-39c6-4d80-abdc-84968e71edb1], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=d03bdbe9-9d87-46bc-9cee-255be56e9c37, parentId=root, childrenIds=[f92b3011-4059-4ec0-900c-a9ac29f80aa2], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=f92b3011-4059-4ec0-900c-a9ac29f80aa2, parentId=d03bdbe9-9d87-46bc-9cee-255be56e9c37, childrenIds=[87f2fe71-2a97-4186-9c0a-6e895d4af779], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=87f2fe71-2a97-4186-9c0a-6e895d4af779, parentId=f92b3011-4059-4ec0-900c-a9ac29f80aa2, childrenIds=[ca148a60-561b-4cef-aeff-396106d5b141], san=f4, from=(5, 6), to=(5, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/4PP2/8/PPPP2PP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=ca148a60-561b-4cef-aeff-396106d5b141, parentId=87f2fe71-2a97-4186-9c0a-6e895d4af779, childrenIds=[3695316d-7146-4d96-a71f-abecaf44fd60], san=Qh4+, from=(3, 0), to=(7, 4), fen=rnb1kbnr/pppp1ppp/8/4p3/4PP1q/8/PPPP2PP/RNBQKBNR b KQkq - 1 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=3695316d-7146-4d96-a71f-abecaf44fd60, parentId=ca148a60-561b-4cef-aeff-396106d5b141, childrenIds=[130f3864-b130-4c3a-8128-78397385f8d5], san=g3, from=(6, 6), to=(6, 5), fen=rnb1kbnr/pppp1ppp/8/4p3/4PP1q/6P1/PPPP3P/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=130f3864-b130-4c3a-8128-78397385f8d5, parentId=3695316d-7146-4d96-a71f-abecaf44fd60, childrenIds=[d444fecd-b56b-4213-9560-fdb13fbbe9a9], san=Qxg3+, from=(7, 4), to=(6, 5), fen=rnb1kbnr/pppp1ppp/8/4p3/4PP2/6q1/PPPP3P/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=d444fecd-b56b-4213-9560-fdb13fbbe9a9, parentId=130f3864-b130-4c3a-8128-78397385f8d5, childrenIds=[], san=hxg3, from=(7, 6), to=(6, 5), fen=rnb1kbnr/pppp1ppp/8/4p3/4PP2/6P1/PPPP4/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=88ce8ff9-39c6-4d80-abdc-84968e71edb1, parentId=root, childrenIds=[b14e466c-b77e-4eab-a3c8-314dceecb39c], san=f4, from=(5, 6), to=(5, 4), fen=rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=b14e466c-b77e-4eab-a3c8-314dceecb39c, parentId=88ce8ff9-39c6-4d80-abdc-84968e71edb1, childrenIds=[dacc531f-ed19-4389-ac14-635aa12b6b80], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/5P2/8/PPPPP1PP/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=dacc531f-ed19-4389-ac14-635aa12b6b80, parentId=b14e466c-b77e-4eab-a3c8-314dceecb39c, childrenIds=[9e8699d2-2403-4f24-8bcd-6732d30ddb58], san=g4, from=(6, 6), to=(6, 4), fen=rnbqkbnr/pppp1ppp/8/4p3/5PP1/8/PPPPP2P/RNBQKBNR w KQkq - 0 6, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=9e8699d2-2403-4f24-8bcd-6732d30ddb58, parentId=dacc531f-ed19-4389-ac14-635aa12b6b80, childrenIds=[], san=Qh4#, from=(3, 0), to=(7, 4), fen=rnb1kbnr/pppp1ppp/8/4p3/5PPq/8/PPPPP2P/RNBQKBNR b KQkq - 1 6, isActualMove=true, isWhiteMove=false)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. e4 (1. f4 e5 2. g4 Qh4#) 1... e5 2. f4 Qh4+ 3. g3 Qxg3+ 4. hxg3"
        assertEquals(expected, result)
    }



    @Test
    fun testMovesNodesToChessNotation22(){
        val prueba = "MoveNode(id=root, parentId=null, childrenIds=[905eb903-fe24-438a-b99e-14ff205d95d9], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                "MoveNode(id=905eb903-fe24-438a-b99e-14ff205d95d9, parentId=root, childrenIds=[df0e8253-976f-4dbc-b68d-3f90ccbf11f4], san=h4, from=(7, 6), to=(7, 4), fen=rnbqkbnr/pppppppp/8/8/7P/8/PPPPPPP1/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=df0e8253-976f-4dbc-b68d-3f90ccbf11f4, parentId=905eb903-fe24-438a-b99e-14ff205d95d9, childrenIds=[5990d65c-31a2-480d-b671-994f351a2e86], san=g5, from=(6, 1), to=(6, 3), fen=rnbqkbnr/pppppp1p/8/6p1/7P/8/PPPPPPP1/RNBQKBNR b KQkq - 0 2, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=5990d65c-31a2-480d-b671-994f351a2e86, parentId=df0e8253-976f-4dbc-b68d-3f90ccbf11f4, childrenIds=[7736d884-8f5d-464b-a203-60fb770c7cb5], san=hxg5, from=(7, 4), to=(6, 3), fen=rnbqkbnr/pppppp1p/8/6P1/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=7736d884-8f5d-464b-a203-60fb770c7cb5, parentId=5990d65c-31a2-480d-b671-994f351a2e86, childrenIds=[92396c12-65d3-49be-ad9f-162665278ad8], san=h6, from=(7, 1), to=(7, 2), fen=rnbqkbnr/pppppp2/7p/6P1/8/8/PPPPPPP1/RNBQKBNR b KQkq - 0 3, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=92396c12-65d3-49be-ad9f-162665278ad8, parentId=7736d884-8f5d-464b-a203-60fb770c7cb5, childrenIds=[62d946f9-e0b2-47e9-8938-5c4d326604b9], san=gxh6, from=(6, 3), to=(7, 2), fen=rnbqkbnr/pppppp2/7P/8/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=62d946f9-e0b2-47e9-8938-5c4d326604b9, parentId=92396c12-65d3-49be-ad9f-162665278ad8, childrenIds=[29ee4a3d-8c07-4c52-9a66-2117098a6d40], san=d6, from=(3, 1), to=(3, 2), fen=rnbqkbnr/ppp1pp2/3p3P/8/8/8/PPPPPPP1/RNBQKBNR b KQkq - 0 4, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=29ee4a3d-8c07-4c52-9a66-2117098a6d40, parentId=62d946f9-e0b2-47e9-8938-5c4d326604b9, childrenIds=[88483dd7-3825-454f-ae4d-28210b8dfaf6], san=h7, from=(7, 2), to=(7, 1), fen=rnbqkbnr/ppp1pp1P/3p4/8/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 4, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=88483dd7-3825-454f-ae4d-28210b8dfaf6, parentId=29ee4a3d-8c07-4c52-9a66-2117098a6d40, childrenIds=[afaf0284-8212-4681-9b8f-0a5cf1f6c3b1], san=d5, from=(3, 2), to=(3, 3), fen=rnbqkbnr/ppp1pp1P/8/3p4/8/8/PPPPPPP1/RNBQKBNR b KQkq - 0 5, isActualMove=false, isWhiteMove=false)\n" +
                "MoveNode(id=afaf0284-8212-4681-9b8f-0a5cf1f6c3b1, parentId=88483dd7-3825-454f-ae4d-28210b8dfaf6, childrenIds=[c97cf49b-fa4a-4e0f-a19f-a84060f101cf], san=hxg8=B, from=(7, 1), to=(6, 0), fen=rnbqkbPr/ppp1pp2/8/3p4/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5, isActualMove=false, isWhiteMove=true)\n" +
                "MoveNode(id=c97cf49b-fa4a-4e0f-a19f-a84060f101cf, parentId=afaf0284-8212-4681-9b8f-0a5cf1f6c3b1, childrenIds=[], san=Rxg8, from=(7, 0), to=(6, 0), fen=rnbqkbr1/ppp1pp2/8/3p4/8/8/PPPPPPP1/RNBQKBNR b KQkq - 0 6, isActualMove=true, isWhiteMove=false)"
        val result = PGNHandler.nodeMovesToPgn(parseMoveNodes(prueba).toMutableList())
        val expected = "1. h4 g5 2. hxg5 h6 3. gxh6 d6 4. h7 d5 5. hxg8=B Rxg8"
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